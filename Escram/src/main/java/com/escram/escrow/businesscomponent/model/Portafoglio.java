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
public class Portafoglio implements Serializable{
	private static final long serialVersionUID = -4555149560219465876L;
	
	@Id
	@SequenceGenerator(name = "cliente_seq", sequenceName = "id_cliente_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "cliente_seq")
	private String id;
	
	private long idCliente;
	
	@Column(name="simbolo_crypto", nullable = false)
	private String simboloCrypto;
	
	@Column(name="saldo", nullable = false)
	private double saldo;
	
	@Column(name = "blocked", nullable = false)
	private boolean blocked = false;
	
	@Column(name="qrCode", nullable = false)
	private String qrCode;
}
