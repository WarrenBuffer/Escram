package com.escram.escrow.restcontroller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {	
	private String fromAddress;
	private String toAddress;
	private double amount;
	private String descrizione;
}
