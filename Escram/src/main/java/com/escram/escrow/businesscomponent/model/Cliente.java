package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Cliente implements Serializable{
	private static final long serialVersionUID = 7669044406962086851L;
	
	@Id
	@SequenceGenerator(name = "cliente_seq", sequenceName = "id_cliente_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "cliente_seq")
	private long id;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "blocked", nullable = false)
	private boolean blocked = false;
	
	@OneToMany(targetEntity = Portafoglio.class)
	@JoinColumn(name= "idCliente")
	HashSet<Portafoglio> portafogli=new HashSet<Portafoglio>();
}
