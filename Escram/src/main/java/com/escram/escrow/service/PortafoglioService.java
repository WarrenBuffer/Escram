package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.Portafoglio;

public interface PortafoglioService {
	Portafoglio save(Portafoglio portaFoglio);
	Optional<Portafoglio> findById(String id);
	List<Portafoglio> findAll();
	List<Portafoglio> findAllByIdCliente(long idCliente);
	void delete(Portafoglio portafoglio);
}
