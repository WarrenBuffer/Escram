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
import com.escram.escrow.businesscomponent.model.Notifica;
import com.escram.escrow.businesscomponent.model.Portafoglio;
import com.escram.escrow.businesscomponent.model.RichiestaWithdraw;
import com.escram.escrow.businesscomponent.model.Transazione;
import com.escram.escrow.businesscomponent.model.enums.StatoTransazione;
import com.escram.escrow.businesscomponent.model.enums.TipoTransazione;
import com.escram.escrow.businesscomponent.model.enums.TipologiaCliente;
import com.escram.escrow.restcontroller.CoinRemitterApi;
import com.escram.escrow.restcontroller.utils.CreateInvoice;
import com.escram.escrow.restcontroller.utils.GetInvoice;
import com.escram.escrow.restcontroller.utils.GetNewAddress;
import com.escram.escrow.restcontroller.utils.Withdraw;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.InvoiceService;
import com.escram.escrow.service.NotificaService;
import com.escram.escrow.service.PortafoglioService;
import com.escram.escrow.service.RichiestaWithdrawService;
import com.escram.escrow.service.TransazioneService;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;
import com.escram.escrow.utils.Token;
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
	@Autowired
	NotificaService ns;
	@Autowired
	RichiestaWithdrawService rws;
	
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
		if (email == null || password == null) 
			return new BCResponse(false, "Valori null.");
		
		Optional<Cliente> cliente = cs.findById(email);
		if (cliente.isEmpty())
			return new BCResponse(false, "Nessun utente trovato con email " + email);
		
		if (!BcryptUtil.matches(password, cliente.get().getPassword())) 
			return new BCResponse(false, "Credenziali non valide.");
		
		return new BCResponse(true, Token.generate(email, CLIENT_ROLE));
	}

	public BCResponse getCliente(String email) {
		if (email == null)
			return new BCResponse(false, "Valori null.");
		
		Optional<Cliente> cliente = cs.findById(email);
		if (cliente.isEmpty())
			return new BCResponse(false, "Nessun utente trovato.");
		
		return new BCResponse(true, cliente.get());
	}
	
	public BCResponse creaPortafoglio(String simbolo, String email, String label) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		if (simbolo == null || email == null || label == null) 
			return new BCResponse(false, "Valori null.");
		
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		Optional<Cliente> clienteOpt = cs.findById(email);
		if (cryptoOpt.isEmpty() || clienteOpt.isEmpty())
			return new BCResponse(false, simbolo + " o " + email + " non esistenti.");
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.getNewAddress(new GetNewAddress(TCN_API_KEY, TCN_PASSWORD, label));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() != 1) 
			return new BCResponse(false, "Chiamata a CoinRemitter fallita.");
		
		JsonNode data = rootNode.get("data");
		String indirizzo = data.get("address").asText();
		String qrCode = data.get("qr_code").asText();
		
		Portafoglio portafoglio = new Portafoglio();
		portafoglio.setIndirizzo(indirizzo);
		portafoglio.setEmailCliente(email);
		portafoglio.setQrCode(qrCode);
		portafoglio.setSaldo(0);
		portafoglio.setCreazione(new Date());
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 3);
		portafoglio.setScadenza(cal.getTime());
		portafoglio.setCrypto(cryptoOpt.get());
		
		ps.save(portafoglio);
		return new BCResponse(true, json);
	}
	
	public BCResponse clientWithdrawRequest(String email, String fromIndirizzo, String toIndirizzo, Double importo) {
		if (email == null || fromIndirizzo == null || toIndirizzo == null || importo == null || importo <= 0)
			return new BCResponse(false, "Valori null.");
		
		Optional<Cliente> clienteOpt = cs.findById(email);
		if (clienteOpt.isEmpty()) 
			return new BCResponse(false, "Simbolo non esistente.");
		
		Cliente cliente = clienteOpt.get();
		for (Portafoglio p : cliente.getPortafogli()) {
			if (p.getIndirizzo().equals(fromIndirizzo) && p.getSaldo() > importo) {
				RichiestaWithdraw rw = new RichiestaWithdraw();
				rw.setFromIndirizzo(fromIndirizzo);
				rw.setToIndirizzo(toIndirizzo);
				rw.setImporto(importo);
				rws.save(rw);
				
				return new BCResponse(true, "Richiesta salvata con successo.");
			}
		}
		
		return new BCResponse(false, "From indirizzo non trovato o saldo insufficiente.");
	}
	
	public BCResponse preleva(String simbolo, String toAddress, Double amount) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		if (simbolo == null || toAddress == null || amount == null) 
			return new BCResponse(false, "Valori null.");
		
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		if (cryptoOpt.isEmpty()) {
			return new BCResponse(false, "Simbolo non esistente.");
		}
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		
		String json = apiService.withdraw(new Withdraw(TCN_API_KEY, TCN_PASSWORD, toAddress, amount));
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() != 1) 
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
	
	public BCResponse createInvoice(String simbolo, String fromEmail, String toEmail, Double amount, String descrizione) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		if (simbolo == null || fromEmail == null || toEmail == null || amount == null || descrizione == null) 
			return new BCResponse(false, "Valori null.");
		
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		Optional<Cliente> fromOpt = cs.findById(fromEmail);
		Optional<Cliente> toOpt = cs.findById(toEmail);
		
		if (cryptoOpt.isEmpty() || fromOpt.isEmpty() || toOpt.isEmpty()) {
			return new BCResponse(false, "Simbolo non esistente.");
		}
		
		if (fromOpt.get().getTipologia() != TipologiaCliente.VENDITORE || toOpt.get().getTipologia() != TipologiaCliente.COMPRATORE) {
			return new BCResponse(false, "Il from deve essere un venditore, il to un compratore.");
		}
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.createInvoice(new CreateInvoice(TCN_API_KEY, TCN_PASSWORD, amount, simbolo, "USD", INVOICE_EXPIRATION, NOTIFY_URL, SUCCESS_URL, FAIL_URL, descrizione, "", ""));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() != 1) 
			return new BCResponse(false, "Chiamata a CoinRemitter fallita.");
		
		JsonNode data = rootNode.get("data");
		String id = data.get("id").asText();
		String invoiceId = data.get("invoice_id").asText();
		double usdAmount = data.get("usd_amount").asDouble();
		String status = data.get("status").asText();
		String coin = data.get("coin").asText();
		String url = data.get("url").asText();
		
		Date dataApertura = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, INVOICE_EXPIRATION);
		Date dataScadenza = cal.getTime();
		
		Invoice invoice = new Invoice(id, invoiceId, usdAmount, descrizione, status, coin, url, fromEmail, toEmail, dataApertura, dataScadenza, StatoTransazione.IN_ATTESA, StatoTransazione.IN_ATTESA);
		is.save(invoice);
		
		Notifica notifica = new Notifica();
		notifica.setEmailCliente(toEmail);
		notifica.setUrlInvoice(url);
		ns.save(notifica);
		
		return new BCResponse(true, json);
	}

	public BCResponse getInvoice(String simbolo, String invoiceId) throws URISyntaxException, JsonMappingException, JsonProcessingException {
		if (simbolo == null || invoiceId == null) 
			return new BCResponse(false, "Valori null.");
		
		
		Optional<Crypto> cryptoOpt = crys.findById(simbolo);
		Optional<Invoice> invoiceOpt = is.findByInvoiceId(invoiceId);
		if (cryptoOpt.isEmpty() || invoiceOpt.isEmpty()) {
			return new BCResponse(false, "Simbolo o invoice non esistente.");
		}
		
		URI apiUri = new URI(COIN_REMITTER_URL + simbolo);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.getInvoice(new GetInvoice(TCN_API_KEY, TCN_PASSWORD, invoiceOpt.get().getInvoiceId()));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		
		if (rootNode.get("flag").asInt() != 1) 
			return new BCResponse(false, "Chiamata a CoinRemitter fallita.");
		
		JsonNode data = rootNode.get("data");
		String status = data.get("status").asText();
		
		Invoice invoice = invoiceOpt.get();
		invoice.setStatus(status);
		is.save(invoice);
		
		return new BCResponse(true, json);
	}
	
	public BCResponse annullaInvoice(String email, String invoiceId) {
		if (email == null || invoiceId == null)
			return new BCResponse(false, "Valori null.");
		
		Optional<Cliente> clienteOpt = cs.findById(email);
		Optional<Invoice> invoiceOpt = is.findByInvoiceId(invoiceId);
		
		if (clienteOpt.isEmpty() || invoiceOpt.isEmpty()) 
			return new BCResponse(false, "Cliente o invoice non esistenti.");
		
		Invoice invoice = invoiceOpt.get();
		if (clienteOpt.get().getTipologia() == TipologiaCliente.COMPRATORE) {
			invoice.setStatoDst(StatoTransazione.ANNULLATO);
		} else {
			invoice.setStatoSrc(StatoTransazione.ANNULLATO);
		}
		
		is.save(invoice);
			
		return new BCResponse(true, "Invoice annullato correttamente.");
	}

	public BCResponse confermaInvoice(String email, String invoiceId) {
		if (email == null || invoiceId == null)
			return new BCResponse(false, "Valori null.");
		
		Optional<Cliente> clienteOpt = cs.findById(email);
		Optional<Invoice> invoiceOpt = is.findByInvoiceId(invoiceId);
		
		if (clienteOpt.isEmpty() || invoiceOpt.isEmpty()) 
			return new BCResponse(false, "Cliente o invoice non esistenti.");
		
		Invoice invoice = invoiceOpt.get();
		if (clienteOpt.get().getTipologia() == TipologiaCliente.COMPRATORE) {
			invoice.setStatoDst(StatoTransazione.CONFERMATO);
		} else {
			invoice.setStatoSrc(StatoTransazione.CONFERMATO);
		}
		
		is.save(invoice);
			
		return new BCResponse(true, "Invoice confermato correttamente.");	
	}
	
	public BCResponse getCoinRate() throws URISyntaxException {
		URI apiUri = new URI(COIN_REMITTER_URL);
		CoinRemitterApi apiService = RestClientBuilder.newBuilder().baseUri(apiUri).build(CoinRemitterApi.class);
		String json = apiService.getCoinRate();
		
		return new BCResponse(true, json);
	}
	
	public BCResponse getCrypto() {
		return new BCResponse(true, crys.findAll());
	}
	
}
