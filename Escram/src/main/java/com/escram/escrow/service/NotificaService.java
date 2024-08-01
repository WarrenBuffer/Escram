package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.Notifica;

public interface NotificaService {
	List<Notifica> findAll(); 
	Notifica save(Notifica notifica);
	Optional<Notifica> findById(Long id);
	void delete(Notifica notifica);
}
