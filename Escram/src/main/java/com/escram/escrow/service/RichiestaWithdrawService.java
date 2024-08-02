package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.RichiestaWithdraw;

public interface RichiestaWithdrawService {
	List<RichiestaWithdraw> findAll();
	RichiestaWithdraw save(RichiestaWithdraw richiestaWithdraw);
	Optional<RichiestaWithdraw> findById(long id);
	void delete(RichiestaWithdraw richiestaWithdraw);
}
