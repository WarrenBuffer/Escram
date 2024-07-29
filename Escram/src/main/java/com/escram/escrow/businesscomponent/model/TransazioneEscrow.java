package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;

import com.escram.enums.StatoTransazioneEscrowDst;
import com.escram.enums.StatoTransazioneEscrowSrc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

	@Column(name = "id_portafoglio_src", nullable = false)
	private long idPortafoglioSrc;
	@Column(name = "id_portafoglio_dst", nullable = false)
	private long idPortafoglioDst;
	@Column(name = "importo", nullable = false)
	private double importo;
	@Column(name = "data_apertura", nullable = false)
	private Date dataApertura;
	@Column(name = "data_scadenza", nullable = false)
	private Date dataScadenza;
	@Column(name = "stato_transazione_escrow_src", nullable = false)
	private StatoTransazioneEscrowSrc statoSrc;
	@Column(name = "stato_transazione_escrow_dst", nullable = false)
	private StatoTransazioneEscrowDst statoDst;
	@Column(name = "descrizione", nullable = false)
	private String descrizione;
}
