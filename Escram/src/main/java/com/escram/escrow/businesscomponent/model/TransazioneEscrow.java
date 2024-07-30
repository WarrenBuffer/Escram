package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class TransazioneEscrow implements Serializable {

	private static final long serialVersionUID = -5487510670389101410L;

	@Id
	@SequenceGenerator(name = "transazione_escrow_seq", sequenceName = "transazione_escrow_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "transazione_escrow_seq")
	private long idTransazione;
	
	@JoinColumn(name = "indirizzoSrc")
	private String indirizzoSrc;
	@JoinColumn(name = "indirizzoDst")
	private String indirizzoDst;
	@Column(name = "importo", nullable = false)
	private double importo;
	@Column(name = "dataApertura", nullable = false)
	private Date dataApertura;
	@Column(name = "dataScadenza", nullable = false)
	private Date dataScadenza;
	@Column(name = "statoSrc", nullable = false)
	private StatoTransazione statoSrc;
	@Column(name = "statoDst", nullable = false)
	private StatoTransazione statoDst;
	@Column(name = "descrizione", nullable = false)
	private String descrizione;
}
