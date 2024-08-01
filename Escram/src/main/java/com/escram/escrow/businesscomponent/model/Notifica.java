package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifica implements Serializable{
	private static final long serialVersionUID = -1440474521335814979L;
	
	@Id
    @SequenceGenerator(name = "notificaSeq", sequenceName = "notificaIdSeq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "notificaSeq")
	private long id;
	@JoinColumn(name = "email")
	private String emailCliente;	
	@JoinColumn(name = "id")
	private String invoiceId;
	
}
