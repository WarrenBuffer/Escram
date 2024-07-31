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
import com.escram.escrow.businesscomponent.model.Portafoglio;
import com.escram.escrow.businesscomponent.model.Transazione;
import com.escram.escrow.businesscomponent.model.enums.TipoTransazione;
import com.escram.escrow.restcontroller.CoinRemitterApi;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.PortafoglioService;
import com.escram.escrow.service.TransazioneService;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;
import com.escram.escrow.utils.GetNewAddress;
import com.escram.escrow.utils.Withdraw;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
