package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;

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
public class Cliente implements Serializable {
	private static final long serialVersionUID = 192839884774093L;

	@Id
	@SequenceGenerator(name = "cliente_seq", sequenceName = "id_cliente_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "cliente_seq")
	long id;
	@Column(name = "nome", nullable = false)
	String nome;
	@Column(name = "cognome", nullable = false)
	String cognome;
	@Column(name = "email", nullable = false)
	String email;
	@Column(name = "password", nullable = false)
	String password;
	@Column(name = "blocked", nullable = false)
	boolean blocked = false;
}
