package com.escram.escrow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Optional<Cliente> findByEmail(String email);
}
