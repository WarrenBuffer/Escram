package com.escram.escrow.restcontroller.utils;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class LoginRequest {
	private String email;
	private String password;
}
