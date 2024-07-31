package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Set;

import com.escram.escrow.businesscomponent.model.enums.TipologiaCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Serializable{
	private static final long serialVersionUID = 7669044406962086851L;
	@Id
	@Column(name = "email")
	private String email;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;
	@Column(name = "blocked", nullable = false)
	private boolean blocked = false;
	@Column(name = "tipologia", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipologiaCliente tipologia;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "emailCliente")
	private Set<Portafoglio> portafogli;
}
