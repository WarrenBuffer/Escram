package com.escram.escrow.restcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.escram.escrow.businesscomponent.AdminBC;
import com.escram.escrow.businesscomponent.model.Admin;
import com.escram.escrow.businesscomponent.model.Cliente;
import com.escram.escrow.businesscomponent.model.Crypto;
import com.escram.escrow.businesscomponent.model.Invoice;
import com.escram.escrow.businesscomponent.model.Portafoglio;
import com.escram.escrow.businesscomponent.model.enums.StatoTransazione;
import com.escram.escrow.businesscomponent.model.enums.TipologiaCliente;
import com.escram.escrow.service.AdminService;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.InvoiceService;
import com.escram.escrow.utils.BCResponse;
import com.escram.escrow.utils.Costanti;
import com.escram.escrow.utils.Token;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS)
@TestHTTPEndpoint(AdminController.class)
@TestMethodOrder(OrderAnnotation.class)
class AdminControllerTest implements Costanti{
	
	@Inject
	AdminController adminController;
	
	@Inject
	InvoiceService invoiceService;
	@Inject
	ClienteService clienteService;
	@Inject
	CryptoService cryptoService;
	@Inject
	AdminService adminService;
	
	@Mock
    AdminBC adminBC;
	
	private Invoice inv1 = new Invoice();
	private Invoice inv2 = new Invoice();
	private Invoice inv3 = new Invoice();
	private Crypto test1 = new Crypto();
	private Crypto test2 = new Crypto();
	private Cliente c1 = new Cliente();
	private Cliente c2 = new Cliente();
	private Admin a = new Admin();
	
	@BeforeAll
	void setUp() {
		
		MockitoAnnotations.openMocks(this);
		
		inv1.setStatoDst(StatoTransazione.CONFERMATO);
		inv1.setStatoSrc(StatoTransazione.CONFERMATO);
		inv1.setDataApertura(new Date());
		inv1.setDataScadenza(new Date());
		inv1.setEmailDst("email@email.it");
		inv1.setEmailSrc("email2@email.com");
		inv1.setId("1");
		inv1.setUrl("urltest1");
		inv1.setInvoiceId("INV1");
		inv1.setStatus("Confermato");
		inv1.setUsdAmount(350);
		
		inv2.setStatoDst(StatoTransazione.CONFERMATO);
		inv2.setStatoSrc(StatoTransazione.IN_ATTESA);
		inv2.setDataApertura(new Date());
		inv2.setDataScadenza(new Date());
		inv2.setEmailDst("email@email.it");
		inv2.setEmailSrc("email2@email.com");
		inv2.setId("2");
		inv2.setUrl("urltest2");
		inv2.setInvoiceId("INV2");
		inv2.setStatus("Confermato");
		inv2.setUsdAmount(350);
		
		inv3.setStatoDst(StatoTransazione.CONFERMATO);
		inv3.setStatoSrc(StatoTransazione.ANNULLATO);
		inv3.setDataApertura(new Date());
		inv3.setDataScadenza(new Date());
		inv3.setEmailDst("email@email.it");
		inv3.setEmailSrc("email2@email.com");
		inv3.setId("3");
		inv3.setUrl("urltest3");
		inv3.setInvoiceId("INV3");
		inv3.setStatus("Confermato");
		inv3.setUsdAmount(350);
		
		test1.setNome("prova");
		test1.setSimbolo("prv");
		test1.setUrlImmagine("urlPRV");
		
		test2.setNome("prova2");
		test2.setSimbolo("prv2");
		test2.setUrlImmagine("urlPRV2");
		
		c1.setNome("Mario");
		c1.setCognome("Rossi");
		c1.setEmail("email@email.it");
		c1.setPassword("pass");
		c1.setBlocked(false);
		c1.setPortafogli(new HashSet<Portafoglio>());
		c1.setTipologia(TipologiaCliente.COMPRATORE);
		
		c2.setNome("Paolo");
		c2.setCognome("Verdi");
		c2.setEmail("email2@email.com");
		c2.setPassword("pass");
		c2.setBlocked(true);
		c2.setPortafogli(new HashSet<Portafoglio>());
		c2.setTipologia(TipologiaCliente.VENDITORE);
		
		a.setEmail("emailAdminTest@email.it");
		a.setNome("Admin");
		a.setCognome("Admin");
		a.setPassword(BcryptUtil.bcryptHash("Pass01?"));
		
		c1 = clienteService.save(c1);
		c2 = clienteService.save(c2);
		test1 = cryptoService.save(test1);
		test2 = cryptoService.save(test2);
		inv1 = invoiceService.save(inv1);
		inv2 = invoiceService.save(inv2);
		inv3 = invoiceService.save(inv3);
		a = adminService.save(a);
	}
	
	@Order(1)
	@Test
	void loginAdminTest() {
		BCResponse bcResponse = new BCResponse(true, Token.generate(a.getEmail(),ADMIN_ROLE));
		when(adminBC.login(a.getEmail(), "Pass01?")).thenReturn(bcResponse);
		
		RestAssured.given().param("email", a.getEmail())
					       .param("password", "Pass01?")
					       .when()
					       .post("loginAdmin")
					       .then()
					       .statusCode(200).extract().response();
		
	}
	@Order(2)
	@Test
	void loginAdminTestUtenteNonTrovato() {
		BCResponse bcResponse = new BCResponse(false, "Nessun utente trovato con email " + "test@email.it");
		when(adminBC.login("test@email.it", "Pass01?")).thenReturn(bcResponse);
		
		Response response = RestAssured.given().param("email", "test@email.it")
					       .param("password", "Pass01?")
					       .when()
					       .post("loginAdmin")
					       .then()
					       .statusCode(400).extract().response();
		assertEquals(bcResponse.getMessage(), response.getBody().asString());
		
	}
	@Order(3)
	@Test
	void loginAdminTestUtenteCredenzialiErrate() {
		BCResponse bcResponse = new BCResponse(false, "Credenziali non valide.");
		when(adminBC.login(a.getEmail(), "PassNonEsistenteTest")).thenReturn(bcResponse);
		
		Response response = RestAssured.given().param("email", a.getEmail())
					       .param("password", "PassNonEsistenteTest")
					       .when()
					       .post("loginAdmin")
					       .then()
					       .statusCode(400).extract().response();

		assertEquals(bcResponse.getMessage(), response.getBody().asString());
		
	}

	@Order(4)
	@Test
	void transazioniIrrisolteTest() {
		List<Invoice> listaExp = invoiceService.irrisolte();
		Response response = RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		                .when()
		                .get("irrisolte")
		                .then()
		                .statusCode(200)
		                .contentType(ContentType.JSON)
		                .extract()
		                .response();
		
		List<Invoice> listaAct = response.getBody().jsonPath().getList("",Invoice.class);
		
		assertEquals(listaExp.size(), listaAct.size());
	}
	@Order(5)
	@Test
	void transazioniCompletateTest() {
		List<Invoice> listaExp = invoiceService.completate();
		Response response = RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		                .when()
		                .get("completate")
		                .then()
		                .statusCode(200)
		                .contentType(ContentType.JSON)
		                .extract()
		                .response();
		
		List<Invoice> listaAct = response.getBody().jsonPath().getList("",Invoice.class);
		
		assertEquals(listaExp.size(), listaAct.size());
	}
	@Order(6)
	@Test
	void transazioniInAttesaTest() {
		List<Invoice> listaExp = invoiceService.inAttesa();
		Response response = RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		                .when()
		                .get("inAttesa")
		                .then()
		                .statusCode(200)
		                .contentType(ContentType.JSON)
		                .extract()
		                .response();
		
		List<Invoice> listaAct = response.getBody().jsonPath().getList("",Invoice.class);
		
		assertEquals(listaExp.size(), listaAct.size());
	}
	@Order(7)
	@Test
	void listaClientiTest() {
		List<Cliente> listaClienti = clienteService.findAll();
		
		Response response = RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
                .when()
                .get("clienti")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
		
		List<Cliente> listaAct = response.getBody().jsonPath().getList("", Cliente.class);
		
		assertEquals(listaClienti.size(), listaAct.size());
		
		clienteService.delete(c1);
		clienteService.delete(c2);
		
		Response responseListaVuota = RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
                .when()
                .get("clienti")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
		
		assertEquals("\"Nessun cliente presente\"", responseListaVuota.getBody().asString());
		
		clienteService.save(c1);
		clienteService.save(c2);
		invoiceService.save(inv1);
		invoiceService.save(inv2);
		invoiceService.save(inv3);
	}
	@Order(8)
	@Test
	void lockUnlockTestClienteBloccato() {		
		RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		.param("email", c2.getEmail()).when().post("lockUnlock").then().statusCode(200);
		
	}
	@Order(9)
	@Test
	void lockUnlockTestClienteSbloccato() {
		c2.setBlocked(false);
		clienteService.save(c2);
		RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		.param("email", c2.getEmail()).when().post("lockUnlock").then().statusCode(200);
	}
	@Order(10)
	@Test
	void lockUnlockTestClienteNonEsiste() {
		RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		.param("email","emaildiTest@email.it").when().post("lockUnlock").then().statusCode(401);
	}
	@Order(11)
	@Test
	void addCryptoTestNonEsiste() {
		Crypto crypto = new Crypto();
		crypto.setNome("CRPTST");
		crypto.setSimbolo("TEST");
		crypto.setUrlImmagine("URLTEST");
		Response response = RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		.param("simbolo",crypto.getSimbolo()).param("nome",crypto.getNome()).param("urlImmagine",crypto.getUrlImmagine()).when().post("addcrypto").then().statusCode(200).extract().response();
		
		assertEquals("Crypto aggiunta correttamente!", response.getBody().asString());
		cryptoService.delete(crypto);
	}
	@Order(12)
	@Test
	void addCryptoTestEsiste() {
		Response response = RestAssured.given().header("Authorization", "Bearer " + Token.generate(a.getEmail(),ADMIN_ROLE))
		.param("simbolo",test1.getSimbolo()).param("nome",test1.getNome()).param("urlImmagine",test1.getUrlImmagine()).when().post("addcrypto")
		.then().statusCode(401).extract().response();
	
		assertEquals("Impossibile aggiungere una crypto esistente!", response.getBody().asString());
	}
	    
	@AfterAll
	void tearDown() {
		invoiceService.delete(inv1);
		invoiceService.delete(inv2);
		invoiceService.delete(inv3);
		clienteService.delete(c1);
		clienteService.delete(c2);
		cryptoService.delete(test1);
		cryptoService.delete(test2);
		adminService.delete(a);
	}
}
