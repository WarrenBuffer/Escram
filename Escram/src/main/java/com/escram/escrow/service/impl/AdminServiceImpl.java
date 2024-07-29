package com.escram.escrow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escram.escrow.businesscomponent.model.Admin;
import com.escram.escrow.repository.AdminRepository;
import com.escram.escrow.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminRepository ar;
	
	@Override
	public List<Admin> findAll() {
		return ar.findAll();
	}

	@Override
	public Admin save(Admin admin) {
		return ar.save(admin);
	}

	@Override
	public Optional<Admin> findById(long id) {
		return ar.findById(id);
	}
	
	@Override
	public Optional<Admin> findByEmail(String email) {
		return ar.findByEmail(email);
	}

	@Override
	public void delete(Admin admin) {
		ar.delete(admin);
	}

	
}
