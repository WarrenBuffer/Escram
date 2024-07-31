package com.escram.escrow.restcontroller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoice {
	private String api_key;
	private String password;
	private double amount;
	private String name;
	private String currency;
	private int expire_time; // in minuti
	private String notify_url;
	private String success_url;
	private String fail_url;
	private String description;
	private String custom_data1;
	private String custom_data2;
}
