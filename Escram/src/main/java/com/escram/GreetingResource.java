package com.escram;

import org.springframework.beans.factory.annotation.Autowired;

import com.escram.escrow.service.ClienteService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {
	@Autowired
	ClienteService cs;
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}
