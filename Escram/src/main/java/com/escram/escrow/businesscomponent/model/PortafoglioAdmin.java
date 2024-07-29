package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class PortafoglioAdmin implements Serializable{
	private static final long serialVersionUID = 8674692131502808987L;

	@Id
	private String indirizzo;
	
	private long idAdmin;
	
	@Column(name="simbolo_crypto", nullable = false)
	private String simboloCrypto;
	
	@Column(name="saldo", nullable = false)
	private double saldo;
	
	@Column(name="qrCode", nullable = false)
	private String qrCode;
	
	@Column(name="apiKey", nullable = false)
	private String apiKey;
	
	@Column(name="creazione", nullable = false)
	private Date creazione;
	
	@Column(name="scadenza", nullable = false)
	private Date scadenza;
	
	@Column(name="password", nullable= false)
	private String password;
}