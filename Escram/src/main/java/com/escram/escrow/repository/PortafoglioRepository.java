package com.escram.escrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Portafoglio;

@Repository
public interface PortafoglioRepository extends JpaRepository<Portafoglio, String>{
}
