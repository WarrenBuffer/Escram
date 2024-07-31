package com.escram.escrow.restcontroller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Withdraw {
	private String api_key;
	private String password;
	private String to_address;
	private double amount;
}
