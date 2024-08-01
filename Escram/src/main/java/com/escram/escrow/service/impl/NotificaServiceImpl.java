package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.Notifica;
import com.escram.escrow.repository.NotificaRepository;
import com.escram.escrow.service.NotificaService;

@Service
public class NotificaServiceImpl implements NotificaService{
	@Autowired
	NotificaRepository nr;
	
	@Override
	public List<Notifica> findAll() {
		return nr.findAll();
	}

	@Override
	public Notifica save(Notifica notifica) {
		return nr.save(notifica);
	}

	@Override
	public Optional<Notifica> findById(Long id) {
		return nr.findById(id);
	}

	@Override
	public void delete(Notifica notifica) {
		nr.delete(notifica);
	}

}
