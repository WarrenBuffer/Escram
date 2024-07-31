package com.escram.escrow.restcontroller;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;
import org.springframework.beans.factory.annotation.Autowired;

import com.escram.escrow.businesscomponent.ClienteBC;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/cliente")
public class ClienteController implements Costanti {
	@Autowired
	ClienteBC clienteBC;

	@Path("/login")
	@POST
	public Response loginCliente(@RestForm String email, @RestForm String password) {
		return Response.status(Response.Status.OK).entity("DIO INFAME").build();
	}

	@Path("/creaPortafoglio/{simbolo}")
	@POST
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
}
