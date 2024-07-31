package com.escram.escrow.service;

import java.util.List;
import java.util.Optional;

import com.escram.escrow.businesscomponent.model.Admin;

public interface AdminService {
	List<Admin> findAll();
	Admin save(Admin admin);
	Optional<Admin> findById(String email);
	void delete(Admin admin);
}
