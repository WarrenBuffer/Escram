package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.Invoice;
import com.escram.escrow.repository.InvoiceRepository;
import com.escram.escrow.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService{
	@Autowired
	InvoiceRepository ir;
	

	@Override
	public List<Invoice> findAll() {
		return ir.findAll();
	}

	@Override
	public Invoice save(Invoice invoice) {
		return ir.save(invoice);
	}

	@Override
	public Optional<Invoice> findById(String id) {
		return ir.findById(id);
	}

	@Override
	public void delete(Invoice invoice) {
		ir.delete(invoice);
	}

	@Override
	public List<Invoice> irrisolte() {
		return ir.irrisolte();
	}

	@Override
	public List<Invoice> completate() {
		return ir.completate();
	}

	@Override
	public List<Invoice> inAttesa() {
		return ir.inAttesa();
	}

	@Override
	public Optional<Invoice> findByInvoiceId(String invoiceId) {
		return ir.findByInvoiceId(invoiceId);
	}
}
