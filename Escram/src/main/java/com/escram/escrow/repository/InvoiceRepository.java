package com.escram.escrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String>{

	@Query(value="from Invoice where statoDst=StatoTransazione.ANNULLATO or statoSrc=StatoTransazione.ANNULLATO")
	List<Invoice> irrisolte();
	
	@Query(value="from Invoice where statoDst=StatoTransazione.CONFERMATO and statoSrc=StatoTransazione.CONFERMATO")
	List<Invoice> completate();
	
	@Query(value="from Invoice where statoDst=StatoTransazione.IN_ATTESA or statoSrc=StatoTransazione.IN_ATTESA")
	List<Invoice> inAttesa();
	
	Optional<Invoice> findByInvoiceId(String invoiceId);
}
