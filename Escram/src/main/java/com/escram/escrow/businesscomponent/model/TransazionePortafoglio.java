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
@Table
@Entity
public class TransazionePortafoglio implements Serializable {

	private static final long serialVersionUID = -5530947559829751851L;

	@Id
	@SequenceGenerator(name = "transazione_portafoglio_seq", sequenceName = "transazione_portafoglio_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "transazione_portafoglio_seq")
	private long idTransazione;

	@JoinColumn(name = "indirizzo")
	private String indirizzo;
	
	@Column(name = "importo", nullable = false)
	private double importo;
	@Column(name = "tipoTransazione", nullable = false)
	private TipoTransazione tipo;
	@Column(name = "data", nullable = false)
	private Date data;
}
