package com.escram.escrow.businesscomponent;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escram.escrow.businesscomponent.model.Cliente;
import com.escram.escrow.businesscomponent.model.Crypto;
import com.escram.escrow.businesscomponent.model.Invoice;
import com.escram.escrow.businesscomponent.model.Portafoglio;
import com.escram.escrow.businesscomponent.model.Transazione;
import com.escram.escrow.businesscomponent.model.enums.StatoTransazione;
import com.escram.escrow.businesscomponent.model.enums.TipoTransazione;
import com.escram.escrow.businesscomponent.model.enums.TipologiaCliente;
import com.escram.escrow.restcontroller.CoinRemitterApi;
import com.escram.escrow.security.Token;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.InvoiceService;
import com.escram.escrow.service.PortafoglioService;
import com.escram.escrow.service.TransazioneService;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;
import com.escram.escrow.utils.CreateInvoice;
import com.escram.escrow.utils.GetInvoice;
import com.escram.escrow.utils.GetNewAddress;
import com.escram.escrow.utils.Withdraw;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.elytron.security.common.BcryptUtil;

@Component
public class ClienteBC implements Costanti {
	@Autowired
	ClienteService cs;
	@Autowired
	PortafoglioService ps;
	@Autowired 
	CryptoService crys;
	@Autowired 
	TransazioneService ts;
	@Autowired
	InvoiceService is;
	
	private boolean validateCredentials(String nome, String cognome, String email, String password) {
		if (nome == null || cognome == null || email == null || password == null)
			return false;
		if (!nome.matches("^[a-zA-Z ,.'-]{2,30}$"))
			return false;
		if (!cognome.matches("^[a-zA-Z ,.'-]{2,30}$"))
			return false;
		if (!email.matches("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
			return false;
		if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#&%^$?=])[a-zA-Z0-9@#&%^$?=]{8,32}$"))
			return false;
		return true;
	}
	
	public BCResponse signUp(String nome, String cognome, String email, String password, String tipologia) {
		if (!validateCredentials(nome, cognome, email, password)) 
			return new BCResponse(false, "Validazione credenziali fallita.");
		
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setCognome(cognome);
		cliente.setEmail(email);
		cliente.setPassword(BcryptUtil.bcryptHash(password));
		cliente.setTipologia(tipologia.equals("compratore") ? TipologiaCliente.COMPRATORE : TipologiaCliente.VENDITORE);
		cliente.setBlocked(false);
		
		cs.save(cliente);
		
		return new BCResponse(true, "Utente registrato con successo.");
	}
	
	public BCResponse login(String email, String password) {
		Optional<Cliente> cliente = cs.findById(email);
		if (cliente.isEmpty())
			return new BCResponse(false, "Nessun utente trovato con email " + email);
		
		if (!BcryptUtil.matches(password, cliente.get().getPassword())) 
			return new BCResponse(false, "Credenziali non valide.");
		
		return new BCResponse(true, Token.generate(email));
	}
	
	public BCResponse creaPortafoglio(String simbolo, String email, String label) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		Optional<Cliente> clienteOpt = cs.findById(email);
		if (cryptoOpt.isEmpty() || clienteOpt.isEmpty())
			return new BCResponse(false, simbolo + " o " + email + " non esistenti.");
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.getNewAddress(new GetNewAddress(TCN_API_KEY, TCN_PASSWORD, label));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() == 0) 
			return new BCResponse(false, "Chiamata a CoinRemitter fallita.");
		
		JsonNode data = rootNode.get("data");
		String indirizzo = data.get("address").asText();
		String qrCode = data.get("qr_code").asText();
		
		Portafoglio portafoglio = new Portafoglio();
		portafoglio.setIndirizzo(indirizzo);
		portafoglio.setEmailCliente(email);
		portafoglio.setQrCode(qrCode);
		portafoglio.setSaldo(0);
		portafoglio.setBlocked(false);
		portafoglio.setCreazione(new Date());
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 3);
		portafoglio.setScadenza(cal.getTime());
		portafoglio.setCrypto(cryptoOpt.get());
		
		ps.save(portafoglio);
		return new BCResponse(true, json);
	}
	
	public BCResponse preleva(String simbolo, String toAddress, double amount) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		if (cryptoOpt.isEmpty()) {
			return new BCResponse(false, "Simbolo non esistente.");
		}
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		
		String json = apiService.withdraw(new Withdraw(TCN_API_KEY, TCN_PASSWORD, toAddress, amount));
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() == 0) 
			return new BCResponse(false, "Chiamata a CoinRemitter fallita.");
		
		JsonNode data = rootNode.get("data");
		String id = data.get("id").asText();
		String txId = data.get("txid").asText();
		double transFees = data.get("transaction_fees").asDouble();
		double procFees = data.get("processing_fees").asDouble();
		double totalFees = transFees + procFees;
		
		Transazione trans = new Transazione();
		trans.setId(id);
		trans.setTxId(txId);
		trans.setAmount(amount);
		trans.setFees(totalFees);
		trans.setTipo(TipoTransazione.PRELIEVO);
		trans.setDate(new Date());
		trans.setToAddress(toAddress);
		
		ts.save(trans);
		
		return new BCResponse(true, json);
	}
	
	public BCResponse createInvoice(String simbolo, String fromEmail, String toEmail, double amount, String descrizione) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		Optional<Cliente> fromOpt = cs.findById(fromEmail);
		Optional<Cliente> toOpt = cs.findById(toEmail);
		
		if (cryptoOpt.isEmpty() || fromOpt.isEmpty() || toOpt.isEmpty()) {
			return new BCResponse(false, "Simbolo non esistente.");
		}
		
		String fromAddress = null;
		for (Portafoglio p : fromOpt.get().getPortafogli()) {
			if (p.getCrypto().getSimbolo().equals(simbolo))
				fromAddress = p.getIndirizzo();
		}

		String toAddress = null;
		for (Portafoglio p : toOpt.get().getPortafogli()) {
			if (p.getCrypto().getSimbolo().equals(simbolo))
				toAddress = p.getIndirizzo();
		}
		
		if (fromAddress == null || toAddress == null) {
			return new BCResponse(false, "Una delle due email non esiste.");
		}
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.createInvoice(new CreateInvoice(TCN_API_KEY, TCN_PASSWORD, amount, simbolo, "USD", INVOICE_EXPIRATION, NOTIFY_URL, SUCCESS_URL, FAIL_URL, descrizione, "", ""));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() == 0) 
			return new BCResponse(false, "Chiamata a CoinRemitter fallita.");
		
		JsonNode data = rootNode.get("data");
		String id = data.get("id").asText();
		String invoiceId = data.get("invoice_id").asText();
		double usdAmount = data.get("usd_amount").asDouble();
		String status = data.get("status").asText();
		String url = data.get("url").asText();
		
		Date dataApertura = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, INVOICE_EXPIRATION);
		Date dataScadenza = cal.getTime();
		
		Invoice invoice = new Invoice(id, invoiceId, usdAmount, status, url, fromAddress, toAddress, dataApertura, dataScadenza, StatoTransazione.IN_ATTESA, StatoTransazione.IN_ATTESA);
		is.save(invoice);
		
		return new BCResponse(true, json);
	}

	public BCResponse getInvoice(String simbolo, String invoiceId) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		if (cryptoOpt.isEmpty()) {
			return new BCResponse(false, "Simbolo non esistente.");
		}
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.getInvoice(new GetInvoice(TCN_API_KEY, TCN_PASSWORD, invoiceId));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() == 0) 
			return new BCResponse(false, "Chiamata a CoinRemitter fallita.");
		
		return new BCResponse(true, json);
	}
	
	public BCResponse getCoinRate() throws URISyntaxException {
		URI apiUri = new URI(COIN_REMITTER_URL);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.getCoinRate();
		
		return new BCResponse(true, json);
	}
	
	
}
