package com.escram.escrow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	Optional<Admin> findByEmail(String email);
}
