package com.escram.escrow.businesscomponent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Crypto {
	@Id
	String simbolo;
	@Column(name = "urlImmagine", nullable = false)
	String urlImmagine;
}
