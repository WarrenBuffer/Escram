package com.escram.escrow.restcontroller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetInvoice {
	private String api_key;
	private String password;
	private String invoice_id;
}
