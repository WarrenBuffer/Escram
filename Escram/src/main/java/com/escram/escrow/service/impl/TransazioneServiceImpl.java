package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.Transazione;
import com.escram.escrow.repository.TransazioneRepository;
import com.escram.escrow.service.TransazioneService;

@Service
public class TransazioneServiceImpl implements TransazioneService {
	@Autowired
	TransazioneRepository tr;

	@Override
	public List<Transazione> findAll() {
		return tr.findAll();
	}

	@Override
	public Transazione save(Transazione transazione) {
		return tr.save(transazione);
	}

	@Override
	public Optional<Transazione> findById(String id) {
		return tr.findById(id);
	}

	@Override
	public void delete(Transazione transazione) {
		tr.delete(transazione);
	}
	
	

}
