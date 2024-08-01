package com.escram.escrow.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockitoAnnotations;

import com.escram.escrow.businesscomponent.model.Admin;
import com.escram.escrow.service.AdminService;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AdminRepositoryTest {

	@Inject
	AdminService adminService;

	private Admin a = new Admin();
	private Admin b = new Admin();
	private Admin c = new Admin();
	
	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		a.setEmail("emailAdminTestA@email.it");
		a.setNome("AdminA");
		a.setCognome("AdminA");
		a.setPassword(BcryptUtil.bcryptHash("Pass01?"));
		b.setEmail("emailAdminTestB@email.it");
		b.setNome("AdminB");
		b.setCognome("AdminB");
		b.setPassword(BcryptUtil.bcryptHash("Pass01?"));
		c.setEmail("emailAdminTestC@email.it");
		c.setNome("AdminC");
		c.setCognome("AdminC");
		c.setPassword(BcryptUtil.bcryptHash("Pass01?"));
		
		a = adminService.save(a);
		b = adminService.save(b);
		c = adminService.save(c);
	}
	
	@Test
	void adminRepositoryTest() {
		
		List<Admin> listaAdmin = adminService.findAll();
		assertNotEquals(0, listaAdmin.size());
		assertFalse(listaAdmin.isEmpty());
		Admin d = new Admin();
		d.setEmail("emailAdminTestD@email.it");
		d.setNome("AdminD");
		d.setCognome("AdminD");
		d.setPassword(BcryptUtil.bcryptHash("Pass01?"));
		Admin testSave = adminService.save(d);
		assertEquals(d.getEmail(), testSave.getEmail());
		Optional<Admin> adminOptTest = adminService.findById(d.getEmail());
		assertTrue(adminOptTest.isPresent());
		adminService.delete(d);
		
	}
	
	@AfterAll
	void tearDown() {
		adminService.delete(a);
		adminService.delete(b);
		adminService.delete(c);
	}
}
