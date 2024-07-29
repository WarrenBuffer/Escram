package com.escram.escrow.businesscomponent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Admin {
	@Id
	@SequenceGenerator(name = "admin_seq", sequenceName = "id_admin_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "admin_seq")
	long id;
	@Column(name = "nome", nullable = false)
	String nome;
	@Column(name = "cognome", nullable = false)
	String cognome;
	@Column(name = "email", nullable = false, unique = true)
	String email;
	@Column(name = "password", nullable = false)
	String password;
}
