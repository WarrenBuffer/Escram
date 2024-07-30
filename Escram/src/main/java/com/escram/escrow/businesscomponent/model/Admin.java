package com.escram.escrow.businesscomponent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Admin implements Serializable{
	private static final long serialVersionUID = 7231271711327484657L;
	@Id
	@SequenceGenerator(name = "admin_seq", sequenceName = "id_admin_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "admin_seq")
	private long idAdmin;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idAdmin")
	Set<PortafoglioAdmin> portafogli = new HashSet<PortafoglioAdmin>();
}
