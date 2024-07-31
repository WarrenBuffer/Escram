package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;

import com.escram.escrow.businesscomponent.model.enums.TipoTransazione;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Transazione implements Serializable {

	private static final long serialVersionUID = -5487510670389101410L;

	@Id
	private String id;
	
	@Column(name = "txId", nullable = false)
	private String txId;

	@Column(name = "amount", nullable = false)
	private double amount;

	@Column(name = "fees", nullable = false)
	private double fees;
	
	@JoinColumn(name = "toAddress")
	private String toAddress;
	
	@Column(name = "date", nullable = false)
	private Date date;	

	@Column(name = "tipo", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoTransazione tipo;
}
