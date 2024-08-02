package com.escram.escrow.restcontroller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientWithdrawRequest {	
	private String fromIndirizzo;
	private String toIndirizzo;
	private double importo;
}
