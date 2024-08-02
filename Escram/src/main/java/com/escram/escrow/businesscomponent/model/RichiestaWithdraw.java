package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class RichiestaWithdraw implements Serializable{
	private static final long serialVersionUID = 2376427251717581591L;
	
	@Id
    @SequenceGenerator(name = "withdrawSeq", sequenceName = "withdrawIdSeq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "withdrawSeq")
	private long id;
	
	@JoinColumn(name = "fromIndirizzo")
	private String fromIndirizzo;
	
	@Column(name = "toIndirizzo")
	private String toIndirizzo;
	
	@Column(name = "importo")
	private double importo;
}
