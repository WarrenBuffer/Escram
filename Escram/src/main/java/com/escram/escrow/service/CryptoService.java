package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.Crypto;

public interface CryptoService {
	List<Crypto> findAll();
	Crypto save(Crypto crypto);
	Optional<Crypto> findById(String simbolo);
	void delete(Crypto crypto);
}
