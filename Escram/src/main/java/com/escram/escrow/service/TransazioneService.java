package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.Transazione;

public interface TransazioneService {
	List<Transazione> findAll(); 
	Transazione save(Transazione Transazione);
	Optional<Transazione> findById(String id);
	void delete(Transazione Transazione);
}
