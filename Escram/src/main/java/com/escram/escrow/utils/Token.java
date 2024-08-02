package com.escram.escrow.utils;

import java.time.Instant;

import io.smallrye.jwt.build.Jwt;

public class Token {
	
	public static String generate(String email, String role) {
		String token = Jwt.issuer("http://localhost:8080").upn(email)
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(60 * 60 * 24))
				.groups(role)
				.sign();
		
		return token;
	}
}
