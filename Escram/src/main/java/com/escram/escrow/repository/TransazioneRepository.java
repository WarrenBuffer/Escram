package com.escram.escrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Transazione;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, String>{
}
