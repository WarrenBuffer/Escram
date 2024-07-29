package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.Date;

import com.escram.enums.TipoTransazionePortafoglio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
@Entity
public class TransazionePortafoglio implements Serializable {

	private static final long serialVersionUID = -5530947559829751851L;

	@SequenceGenerator(name = "transazione_portafoglio_seq", sequenceName = "transazione_portafoglio_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "transazione_portafoglio_seq")
	private long idTransazione;

	@Column(name = "importo", nullable = false)
	private double importo;
	@Column(name = "tipo_transazione_portafoglio", nullable = false)
	private TipoTransazionePortafoglio tipo;
	@Column(name = "id_portafoglio", nullable = false)
	private long idPortafoglio;
	@Column(name = "data", nullable = false)
	private Date data;

}
