package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.Portafoglio;
import com.escram.escrow.repository.PortafoglioRepository;
import com.escram.escrow.service.PortafoglioService;

@Service
public class PortafoglioServiceImpl implements PortafoglioService{

	@Autowired
	PortafoglioRepository pr;
	
	@Override
	public Portafoglio save(Portafoglio portaFoglio) {
		return pr.save(portaFoglio);
	}

	@Override
	public Optional<Portafoglio> findById(String id) {
		return pr.findById(id);
	}

	@Override
	public List<Portafoglio> findAll() {
		return pr.findAll();
	}

	@Override
	public List<Portafoglio> findAllByIdCliente(long idCliente) {
		return pr.findAllByIdCliente(idCliente);
	}

	@Override
	public void delete(Portafoglio portafoglio) {
		pr.delete(portafoglio);
		
	}
}
