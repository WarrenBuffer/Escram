package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Admin implements Serializable{
	private static final long serialVersionUID = 7231271711327484657L;
	@Id
	@Column(name = "email")
	private String email;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	@Column(name = "password", nullable = false)
	private String password;
}
