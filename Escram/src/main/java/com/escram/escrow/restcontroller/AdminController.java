package com.escram.escrow.restcontroller;

import java.util.List;
import java.util.Optional;

import org.jboss.resteasy.reactive.RestForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.escram.escrow.businesscomponent.AdminBC;
import com.escram.escrow.businesscomponent.model.Cliente;
import com.escram.escrow.businesscomponent.model.Crypto;
import com.escram.escrow.businesscomponent.model.Invoice;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.InvoiceService;
import com.escram.escrow.utils.BCResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.Context;

@Path("/admin")
@RequestScoped
public class AdminController {

	@Inject
	private ClienteService clienteService;
	@Inject
	private CryptoService cryptoService;
	@Inject
	private InvoiceService invoiceService;
	@Inject
	private AdminBC adminBC;
	
	@Path("/loginAdmin")
	@POST
	@PermitAll
	public Response loginAdmin(@RestForm String email, @RestForm String password, @Context SecurityContext ctx) {
		BCResponse bcRes = adminBC.login(email, password);

		if (!bcRes.isOk())
			return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

		return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
	}

	@Path("/attive")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	public Response transazioniAttive() {
		return Response.ok().entity(invoiceService.transazioniAttive()).build();
	}
	
	@Path("/irrisolte")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transazioniIrrisolte() throws JsonProcessingException {
		List<Invoice> invoices = invoiceService.irrisolte();
		return Response.ok(new ObjectMapper().writeValueAsString(invoices)).build();
	}
	
	@Path("/completate")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transazioniCompletate() throws JsonProcessingException {
		List<Invoice> invoices = invoiceService.completate();
		return Response.ok(new ObjectMapper().writeValueAsString(invoices)).build();
	}
	
	@Path("/inAttesa")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transazioniAnnullate() throws JsonProcessingException {
		List<Invoice> invoices = invoiceService.inAttesa();
		return Response.ok(new ObjectMapper().writeValueAsString(invoices)).build();
	}
	
	@Path("/clienti")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response listaClienti() throws JsonProcessingException {
		List<Cliente> listaClienti = clienteService.findAll();
		if(!listaClienti.isEmpty()) {
			return Response.ok(new ObjectMapper().writeValueAsString(listaClienti)).build();
		}
		return Response.ok(new ObjectMapper().writeValueAsString("Nessun cliente presente")).build();
	}
	
	@Path("/lockUnlock")
	@POST
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response bloccaSblocca(@RestForm String email) {
		Optional<Cliente> cliente = clienteService.findById(email);
		if(cliente.isPresent()) {
			if(cliente.get().isBlocked()) {
				cliente.get().setBlocked(false);
			}else {
				cliente.get().setBlocked(true);
			}
			clienteService.save(cliente.get());
			return Response.ok().build();
		}
		return Response.status(Status.UNAUTHORIZED).entity(cliente).build();
	}
	
	@Path("/addcrypto")
	@POST
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response aggiungiCrypto(@RestForm String simbolo, @RestForm String nome, @RestForm String urlImmagine) {
		Crypto crypto = new Crypto();
		crypto.setNome(nome);
		crypto.setSimbolo(simbolo);
		crypto.setUrlImmagine(urlImmagine);
		if(cryptoService.findById(simbolo).isPresent()) {
			return Response.status(Status.UNAUTHORIZED).entity("Impossibile aggiungere una crypto esistente!").build();
		}
		cryptoService.save(crypto);
		return Response.ok().entity("Crypto aggiunta correttamente!").build();
	}
}
