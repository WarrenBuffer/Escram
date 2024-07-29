package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.Cliente;

public interface ClienteService {
	List<Cliente> findAll(); 
	Cliente save(Cliente cliente);
	Optional<Cliente> findById(long id);
	Optional<Cliente> findByEmail(String email);
	void delete(Cliente cliente);
}
