package com.escram.escrow.restcontroller;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestPath;
import org.springframework.beans.factory.annotation.Autowired;

import com.escram.escrow.businesscomponent.ClienteBC;
import com.escram.escrow.restcontroller.utils.CreaPortafoglioRequest;
import com.escram.escrow.restcontroller.utils.CreateInvoiceRequest;
import com.escram.escrow.restcontroller.utils.GetInvoiceRequest;
import com.escram.escrow.restcontroller.utils.LoginRequest;
import com.escram.escrow.restcontroller.utils.SignupRequest;
import com.escram.escrow.restcontroller.utils.WithdrawRequest;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

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
	public Response signUp(SignupRequest request) {
		BCResponse bcRes = clienteBC.signUp(request.getNome(), request.getCognome(), request.getEmail(), request.getPassword(), request.getTipologia());

		if (!bcRes.isOk())
			return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

		return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
	}

	@Path("/login")
	@POST
	@PermitAll
	public Response loginCliente(LoginRequest request) {
		BCResponse bcRes = clienteBC.login(request.getEmail(), request.getPassword());

		if (!bcRes.isOk())
			return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();

		return Response.status(Response.Status.OK).entity("Bearer " + bcRes.getMessage()).build();
	}
	
	@Path("/getCliente")
	@POST
	@RolesAllowed(CLIENT_ROLE)
	public Response getCliente() {
		String email = jwt.getName();
		
		BCResponse bcRes = clienteBC.getCliente(email);
		
		if (!bcRes.isOk())
			return Response.status(Response.Status.BAD_REQUEST).entity(bcRes.getMessage()).build();
		
		return Response.status(Response.Status.OK).entity(bcRes.getMessage()).build();
	}

	@Path("/creaPortafoglio/{simbolo}")
	@POST
	@RolesAllowed(CLIENT_ROLE)
	public Response creaPortafoglio(@RestPath String simbolo, CreaPortafoglioRequest request) {
		try {
			BCResponse bcRes = clienteBC.creaPortafoglio(simbolo, request.getEmail(), request.getLabel());

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
	@RolesAllowed(CLIENT_ROLE)
	public Response withdraw(@RestPath String simbolo, WithdrawRequest request) {
		try {
			BCResponse bcRes = clienteBC.preleva(simbolo, request.getToAddress(), request.getAmount());

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
	@RolesAllowed(CLIENT_ROLE)
	public Response createInvoice(@RestPath String simbolo, CreateInvoiceRequest request) {
		try {
			BCResponse bcRes = clienteBC.createInvoice(simbolo, request.getFromEmail(), request.getToEmail(), request.getAmount(), request.getDescrizione());

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
	@RolesAllowed(CLIENT_ROLE)
	public Response getInvoice(@RestPath String simbolo, GetInvoiceRequest request) {
		try {
			BCResponse bcRes = clienteBC.getInvoice(simbolo, request.getInvoiceId());

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
	@RolesAllowed(CLIENT_ROLE)
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
