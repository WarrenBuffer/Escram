package com.escram.escrow.businesscomponent;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escram.escrow.businesscomponent.model.Admin;
import com.escram.escrow.service.AdminService;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.InvoiceService;
import com.escram.escrow.service.PortafoglioService;
import com.escram.escrow.service.TransazioneService;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;
import com.escram.escrow.utils.Token;

import io.quarkus.elytron.security.common.BcryptUtil;

@Component
public class AdminBC implements Costanti{
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
	AdminService as;
	
	public BCResponse login(String email, String password) {
		Optional<Admin> admin = as.findById(email);
		if (admin.isEmpty())
			return new BCResponse(false, "Nessun utente trovato con email " + email);
		
		if (!BcryptUtil.matches(password, admin.get().getPassword())) 
			return new BCResponse(false, "Credenziali non valide.");
		
		return new BCResponse(true, Token.generate(email, ADMIN_ROLE));
	}
}
