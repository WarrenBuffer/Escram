package com.escram.escrow.security;

import java.time.Instant;

import io.smallrye.jwt.build.Jwt;

public class Token {
	
	public static String generate(String email) {
		String token = Jwt.issuer("http://localhost:8080").upn(email)
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(60 * 60))
				.groups("Authenticated")
				.sign();
		
		return token;
	}
}
