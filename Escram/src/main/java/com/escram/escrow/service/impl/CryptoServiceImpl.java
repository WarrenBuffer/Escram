package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.Crypto;
import com.escram.escrow.repository.CryptoRepository;
import com.escram.escrow.service.CryptoService;

@Service
public class CryptoServiceImpl implements CryptoService {
	@Autowired
	CryptoRepository cr;
	
	@Override
	public List<Crypto> findAll() {
		return cr.findAll();
	}
	
	@Override
	public Crypto save(Crypto crypto) {
		return cr.save(crypto);
	}

	@Override
	public Optional<Crypto> findById(String simbolo) {
		return cr.findById(simbolo);
	}

	@Override
	public void delete(Crypto crypto) {
		cr.delete(crypto);
	}
}
