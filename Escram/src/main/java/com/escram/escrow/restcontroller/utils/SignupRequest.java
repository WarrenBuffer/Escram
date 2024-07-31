package com.escram.escrow.restcontroller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String tipologia;
}
