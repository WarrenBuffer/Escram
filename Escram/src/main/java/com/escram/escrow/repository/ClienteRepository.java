package com.escram.escrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
