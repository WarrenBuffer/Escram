package com.escram.escrow.restcontroller;

import java.util.List;
import java.util.Optional;

import org.jboss.resteasy.reactive.RestPath;
import org.springframework.beans.factory.annotation.Autowired;

import com.escram.escrow.businesscomponent.AdminBC;
import com.escram.escrow.businesscomponent.model.Cliente;
import com.escram.escrow.businesscomponent.model.Crypto;
import com.escram.escrow.businesscomponent.model.RichiestaWithdraw;
import com.escram.escrow.restcontroller.utils.UpdateCryptoRequest;
import com.escram.escrow.restcontroller.utils.DeleteCryptoRequest;
import com.escram.escrow.restcontroller.utils.LockUnlockRequest;
import com.escram.escrow.restcontroller.utils.LoginRequest;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.InvoiceService;
import com.escram.escrow.service.RichiestaWithdrawService;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;
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

@Path("/admin")
@RequestScoped

public class AdminController implements Costanti{
	@Inject
	private ClienteService clienteService;
	@Inject
	private CryptoService cryptoService;
	@Inject
	private InvoiceService invoiceService;
	@Inject
	private AdminBC adminBC;
	@Autowired
	private RichiestaWithdrawService rws;
	
	@Path("/login")
	@POST
	@PermitAll
	public Response loginAdmin(LoginRequest request) {
		BCResponse bcRes = adminBC.login(request.getEmail(), request.getPassword());

		if (!bcRes.isOk())
			return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

		return Response.status(Response.Status.OK).entity("Bearer " + bcRes.getMessage()).build();
	}

//	@Path("/attive")
//	@GET
//	@RolesAllowed(ADMIN_ROLE)
//	public Response transazioniAttive() {
//		return Response.ok().entity(invoiceService.transazioniAttive()).build();
//	}
	
	@Path("/irrisolte")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transazioniIrrisolte() throws JsonProcessingException {
		return Response.ok().entity(invoiceService.irrisolte()).build();
	}
	
	@Path("/completate")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transazioniCompletate() throws JsonProcessingException {
		return Response.ok().entity(invoiceService.completate().isEmpty() ? 0 : invoiceService.completate()).build();
	}
	
	@Path("/inAttesa")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transazioniAnnullate() throws JsonProcessingException {
		return Response.ok().entity(invoiceService.inAttesa().isEmpty() ? 0 : invoiceService.inAttesa()).build();
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
	
	@Path("/getClienti")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	public Response getClienti() throws JsonProcessingException {
		List<Cliente> listaClienti = clienteService.findAll();
		return Response.ok(new ObjectMapper().writeValueAsString(listaClienti)).build();
	}
	
	@Path("/lockUnlock")
	@POST
	@RolesAllowed(ADMIN_ROLE)
	public Response bloccaSblocca(LockUnlockRequest request) {
		Optional<Cliente> cliente = clienteService.findById(request.getEmail());
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
	
	@Path("/updatecrypto")
	@POST
	@RolesAllowed(ADMIN_ROLE)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCrypto(UpdateCryptoRequest request) {
		Optional<Crypto> cryptoOpt = cryptoService.findById(request.getSimbolo());
		if(cryptoOpt.isEmpty()) {
			return Response.status(Status.UNAUTHORIZED).entity("Crypto non trovata.").build();
		}
		
		Crypto crypto = cryptoOpt.get();
		crypto.setNome(request.getNome());
		crypto.setUrlImmagine(request.getUrlImmagine());
		cryptoService.save(crypto);
		return Response.ok().entity("Crypto aggiornata correttamente.").build();
	}
	
	@Path("/getcrypto")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	public Response getCrypto() {
		return Response.ok().entity(cryptoService.findAll()).build();
	}

	@Path("/deletecrypto")
	@POST
	@RolesAllowed(ADMIN_ROLE)
	public Response deleteCrypto(DeleteCryptoRequest request) {
		Optional<Crypto> cryptoOpt = cryptoService.findById(request.getSimbolo());
		if (cryptoOpt.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Crypto non trovata.").build();
		
		cryptoService.delete(cryptoOpt.get());
		return Response.ok().entity(cryptoService.findAll()).build();
	}
	
	@Path("/getwithdrawrequests")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	public Response getWithdrawRequests() {
		return Response.ok().entity(rws.findAll()).build();
	}
	
	@Path("/cancelwithdrawrequest/{id}")
	@GET
	@RolesAllowed(ADMIN_ROLE)
	public Response getWithdrawRequests(@RestPath long id) {
		Optional<RichiestaWithdraw> rw = rws.findById(id);
		if (rw.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Richiesta non trovata.").build();
		
		rws.delete(rw.get());
		return Response.ok().entity("Richiesta cancellata con successo.").build();
	}
}
