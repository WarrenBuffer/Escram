package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.Cliente;
import com.escram.escrow.repository.ClienteRepository;
import com.escram.escrow.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{
	@Autowired
	ClienteRepository cr;
	
	@Override
	public List<Cliente> findAll() {
		return cr.findAll();
	}
	
	@Override
	public Cliente save(Cliente cliente) {
		return cr.save(cliente);
	}

	@Override
	public Optional<Cliente> findById(String email) {
		return cr.findById(email);
	}

	@Override
	public void delete(Cliente cliente) {
		cr.delete(cliente);
	}
}
