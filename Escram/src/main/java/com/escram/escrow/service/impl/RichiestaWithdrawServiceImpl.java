package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.RichiestaWithdraw;
import com.escram.escrow.repository.RichiestaWithdrawRepository;
import com.escram.escrow.service.RichiestaWithdrawService;

@Service
public class RichiestaWithdrawServiceImpl implements RichiestaWithdrawService{
	@Autowired
	RichiestaWithdrawRepository rwr;
	
	@Override
	public List<RichiestaWithdraw> findAll() {
		return rwr.findAll();
	}

	@Override
	public RichiestaWithdraw save(RichiestaWithdraw richiestaWithdraw) {
		return rwr.save(richiestaWithdraw);
	}

	@Override
	public Optional<RichiestaWithdraw> findById(long id) {
		return rwr.findById(id);
	}

	@Override
	public void delete(RichiestaWithdraw richiestaWithdraw) {
		rwr.delete(richiestaWithdraw);
	}
}
