package com.escram.escrow.restcontroller;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;
import org.springframework.beans.factory.annotation.Autowired;

import com.escram.escrow.businesscomponent.ClienteBC;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/cliente")
@RequestScoped
public class ClienteController implements Costanti {
	@Inject
	JsonWebToken jwt;

	@Autowired
	ClienteBC clienteBC;

	@Path("/signup")
	@POST
	@PermitAll
	public Response signUp(@RestForm String nome, @RestForm String cognome, @RestForm String email,
			@RestForm String password, @RestForm String tipologia) {
		BCResponse bcRes = clienteBC.signUp(nome, cognome, email, password, tipologia);

		if (!bcRes.isOk())
			return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

		return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
	}

	@Path("/login")
	@POST
	@PermitAll
	public Response loginCliente(@RestForm String email, @RestForm String password, @Context SecurityContext ctx) {
		BCResponse bcRes = clienteBC.login(email, password);

		if (!bcRes.isOk())
			return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

		return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
	}

	@Path("/creaPortafoglio/{simbolo}")
	@POST
	@RolesAllowed("Authenticated")
	public Response creaPortafoglio(@RestPath String simbolo, @RestForm String email, @RestForm String label) {
		try {
			BCResponse bcRes = clienteBC.creaPortafoglio(simbolo, email, label);

			if (!bcRes.isOk())
				return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

			return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("/withdraw/{simbolo}")
	@POST
	@RolesAllowed("Authenticated")
	public Response withdraw(@RestForm String toAddress, @RestForm double amount, @RestPath String simbolo) {
		try {
			BCResponse bcRes = clienteBC.preleva(simbolo, toAddress, amount);

			if (!bcRes.isOk())
				return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

			return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("/createInvoice/{simbolo}")
	@POST
	@RolesAllowed("Authenticated")
	public Response createInvoice(@RestPath String simbolo, @RestForm String fromAddress, @RestForm String toAddress,
			@RestForm double amount, @RestForm String descrizione) {
		try {
			BCResponse bcRes = clienteBC.createInvoice(simbolo, fromAddress, toAddress, amount, descrizione);

			if (!bcRes.isOk())
				return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

			return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("/getInvoice/{simbolo}")
	@POST
	@RolesAllowed("Authenticated")
	public Response getInvoice(@RestPath String simbolo, @RestForm String invoiceId) {
		try {
			BCResponse bcRes = clienteBC.getInvoice(simbolo, invoiceId);

			if (!bcRes.isOk())
				return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

			return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("/getCoinRate")
	@GET
	@RolesAllowed("Authenticated")
	public Response getCoinRate() {
		try {
			BCResponse bcRes = clienteBC.getCoinRate();

			if (!bcRes.isOk())
				return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

			return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
