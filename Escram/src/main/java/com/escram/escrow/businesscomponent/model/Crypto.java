package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Crypto implements Serializable{
	private static final long serialVersionUID = 7982091987549324778L;
	
	@Id
	String simbolo;
	@Column(name = "urlImmagine", nullable = false)
	String urlImmagine;
}
