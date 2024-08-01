package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.Invoice;

public interface InvoiceService {
	List<Invoice> findAll();
	Invoice save(Invoice invoice);
	Optional<Invoice> findById(String id);
	void delete(Invoice invoice);
	long transazioniAttive();
	List<Invoice> irrisolte();
	List<Invoice> completate();
	List<Invoice> inAttesa();
}
