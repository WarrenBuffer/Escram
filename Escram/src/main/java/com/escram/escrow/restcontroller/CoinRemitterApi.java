package com.escram.escrow.restcontroller;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.escram.escrow.utils.Costanti;
import com.escram.escrow.utils.GetNewAddress;
import com.escram.escrow.utils.ValidateAddress;
import com.escram.escrow.utils.Withdraw;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@RegisterRestClient
public interface CoinRemitterApi extends Costanti {
	@POST
	@Path("get-new-address")
	String getNewAddress(GetNewAddress request);
	@POST
	@Path("validate-address")
	String validateAddress(ValidateAddress request);
	@POST
	@Path("withdraw")
	String withdraw(Withdraw request);
}
