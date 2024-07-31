package com.escram.escrow.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNewAddress {
	private String api_key;
	private String password;
	private String label;
}
