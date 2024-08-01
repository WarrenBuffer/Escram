package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;

import com.escram.escrow.businesscomponent.model.enums.StatoTransazione;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice implements Serializable{
	private static final long serialVersionUID = -7279521160563675337L;

	@Id
	private String id;
	
	@Column(name = "invoiceId", nullable = false)
	private String invoiceId;
	
	@Column(name = "usdAmount", nullable = false)
	private double usdAmount;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "url", nullable = false)
	private String url;
	
	@JoinColumn(name = "emailSrc")
	private String emailSrc;
	
	@JoinColumn(name = "emailDst")
	private String emailDst;
	
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

