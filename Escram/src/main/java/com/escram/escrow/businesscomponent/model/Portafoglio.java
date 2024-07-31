package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Portafoglio implements Serializable{
	private static final long serialVersionUID = -4555149560219465876L;
	
	@Id
	private String indirizzo;
	
	@JoinColumn(name = "emailCliente")
	private String emailCliente;
	
	@ManyToOne
	@JoinColumn(name = "simbolo")
	private Crypto crypto;
	
	@Column(name="saldo", nullable = false)
	private double saldo;
	
	@Column(name = "blocked", nullable = false)
	private boolean blocked = false;
	
	@Column(name="qrCode", nullable = false)
	private String qrCode;

	@Column(name="creazione", nullable = false)
	private Date creazione;
	
	@Column(name="scadenza", nullable = false)
	private Date scadenza;
	
	@OneToMany(mappedBy = "toAddress")
	private Set<Transazione> transazioni;

	@OneToMany(mappedBy = "indirizzoSrc")
	private Set<Invoice> invoices;
}
