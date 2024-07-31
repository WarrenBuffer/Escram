package com.escram.escrow.businesscomponent.model;

import java.util.Date;

import com.escram.escrow.businesscomponent.model.enums.StatoTransazione;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table
public class Invoice {
	@Id
	private String id;
	
	@Column(name = "invoiceId", nullable = false)
	private String invoiceId;
	
	@Column(name = "usdAmount", nullable = false)
	private double usdAmount;

	@Column(name = "status", nullable = false)
	private String status;
	
	@JoinColumn(name = "indirizzoSrc")
	private String indirizzoSrc;
	
	@JoinColumn(name = "indirizzoDst")
	private String indirizzoDst;
	
	@Column(name = "dataApertura", nullable = false)
	private Date dataApertura;
	
	@Column(name = "dataScadenza", nullable = false)
	private Date dataScadenza;
	
	@Column(name = "statoSrc", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatoTransazione statoSrc;
	
	@Column(name = "statoDst", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatoTransazione statoDst;
}
