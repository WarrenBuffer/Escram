package com.escram.escrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Notifica;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica, Long>{
	
}
