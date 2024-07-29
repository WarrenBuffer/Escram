package com.escram.escrow.service.impl;

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
	public Cliente save(Cliente cliente) {
		return cr.save(cliente);
	}
	
}
