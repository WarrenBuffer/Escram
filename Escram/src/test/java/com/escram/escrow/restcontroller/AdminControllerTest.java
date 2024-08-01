//package com.escram.escrow.restcontroller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestInstance.Lifecycle;
//
//import com.escram.escrow.businesscomponent.model.Cliente;
//import com.escram.escrow.businesscomponent.model.Crypto;
//import com.escram.escrow.businesscomponent.model.Invoice;
//import com.escram.escrow.businesscomponent.model.Portafoglio;
//import com.escram.escrow.businesscomponent.model.enums.StatoTransazione;
//import com.escram.escrow.businesscomponent.model.enums.TipologiaCliente;
//import com.escram.escrow.service.ClienteService;
//import com.escram.escrow.service.CryptoService;
//import com.escram.escrow.service.InvoiceService;
//
//import io.quarkus.elytron.security.common.BcryptUtil;
//import io.quarkus.test.common.http.TestHTTPEndpoint;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import jakarta.inject.Inject;
//
//@QuarkusTest
//@TestInstance(Lifecycle.PER_CLASS)
//@TestHTTPEndpoint(AdminController.class)
//class AdminControllerTest {
//	
//	@Inject
//	InvoiceService invoiceService;
//	@Inject
//	ClienteService clienteService;
//	@Inject
//	CryptoService cryptoService;
//	
//	private Invoice inv1 = new Invoice();
//	private Invoice inv2 = new Invoice();
//	private Invoice inv3 = new Invoice();
//	private Crypto test1 = new Crypto();
//	private Crypto test2 = new Crypto();
//	private Cliente c1 = new Cliente();
//	private Cliente c2 = new Cliente();
//	
//	@BeforeAll
//	void setUp() {
//		inv1.setStatoDst(StatoTransazione.CONFERMATO);
//		inv1.setStatoSrc(StatoTransazione.CONFERMATO);
//		inv1.setDataApertura(new Date());
//		inv1.setDataScadenza(new Date());
//		inv1.setIndirizzoDst("123456");
//		inv1.setIndirizzoSrc("654321");
//		inv1.setId("1");
//		inv1.setUrl("urltest1");
//		inv1.setInvoiceId("INV1");
//		inv1.setStatus("Confermato");
//		inv1.setUsdAmount(350);
//		
//		inv2.setStatoDst(StatoTransazione.CONFERMATO);
//		inv2.setStatoSrc(StatoTransazione.IN_ATTESA);
//		inv2.setDataApertura(new Date());
//		inv2.setDataScadenza(new Date());
//		inv2.setIndirizzoDst("1234567");
//		inv2.setIndirizzoSrc("7654321");
//		inv2.setId("2");
//		inv2.setUrl("urltest2");
//		inv2.setInvoiceId("INV2");
//		inv2.setStatus("Confermato");
//		inv2.setUsdAmount(350);
//		
//		inv3.setStatoDst(StatoTransazione.CONFERMATO);
//		inv3.setStatoSrc(StatoTransazione.ANNULLATO);
//		inv3.setDataApertura(new Date());
//		inv3.setDataScadenza(new Date());
//		inv3.setIndirizzoDst("12345678");
//		inv3.setIndirizzoSrc("87654321");
//		inv3.setId("3");
//		inv3.setUrl("urltest3");
//		inv3.setInvoiceId("INV3");
//		inv3.setStatus("Confermato");
//		inv3.setUsdAmount(350);
//		
//		test1.setNome("prova");
//		test1.setSimbolo("prv");
//		test1.setUrlImmagine("urlPRV");
//		
//		test2.setNome("prova2");
//		test2.setSimbolo("prv2");
//		test2.setUrlImmagine("urlPRV2");
//		
//		c1.setNome("Mario");
//		c1.setCognome("Rossi");
//		c1.setEmail("email@email.it");
//		c1.setPassword("pass");
//		c1.setBlocked(false);
//		c1.setPortafogli(new HashSet<Portafoglio>());
//		c1.setTipologia(TipologiaCliente.COMPRATORE);
//		
//		c2.setNome("Paolo");
//		c2.setCognome("Verdi");
//		c2.setEmail("email2@email.com");
//		c2.setPassword("pass");
//		c2.setBlocked(true);
//		c2.setPortafogli(new HashSet<Portafoglio>());
//		c2.setTipologia(TipologiaCliente.VENDITORE);
//		
//		c1 = clienteService.save(c1);
//		c2 = clienteService.save(c2);
//		test1 = cryptoService.save(test1);
//		test2 = cryptoService.save(test2);
//		inv1 = invoiceService.save(inv1);
//		inv2 = invoiceService.save(inv2);
//		inv3 = invoiceService.save(inv3);
//		
//		System.out.println(BcryptUtil.bcryptHash("Pass01?"));
//	}
//	    
//	@Test
//	void transazioniAttiveTest() {
//		
//		assertEquals(2,invoiceService.transazioniAttive());
//		
//		RestAssured.given().when().get("attive").then().statusCode(200);
//		
//		invoiceService.delete(inv2);
//		
//		assertEquals(1,invoiceService.transazioniAttive());
//		
//		RestAssured.given().when().get("attive").then().statusCode(200);
//		
//		invoiceService.save(inv2);
//	}
//	
//	@Test
//	void transazioniIrrisolteTest() {
//		List<Invoice> listaExp = invoiceService.irrisolte();
//		Response response = RestAssured.given()
//		                .when()
//		                .get("irrisolte")
//		                .then()
//		                .statusCode(200)
//		                .contentType(ContentType.JSON)
//		                .extract()
//		                .response();
//		
//		List<Invoice> listaAct = response.getBody().jsonPath().getList("",Invoice.class);
//		
//		assertEquals(listaExp.size(), listaAct.size());
//	}
//	
//	@Test
//	void transazioniCompletateTest() {
//		List<Invoice> listaExp = invoiceService.completate();
//		Response response = RestAssured.given()
//		                .when()
//		                .get("completate")
//		                .then()
//		                .statusCode(200)
//		                .contentType(ContentType.JSON)
//		                .extract()
//		                .response();
//		
//		List<Invoice> listaAct = response.getBody().jsonPath().getList("",Invoice.class);
//		
//		assertEquals(listaExp.size(), listaAct.size());
//	}
//	
//	@Test
//	void transazioniInAttesaTest() {
//		List<Invoice> listaExp = invoiceService.inAttesa();
//		Response response = RestAssured.given()
//		                .when()
//		                .get("inAttesa")
//		                .then()
//		                .statusCode(200)
//		                .contentType(ContentType.JSON)
//		                .extract()
//		                .response();
//		
//		List<Invoice> listaAct = response.getBody().jsonPath().getList("",Invoice.class);
//		
//		assertEquals(listaExp.size(), listaAct.size());
//	}
//	
//	@Test
//	void listaClientiTest() {
//		List<Cliente> listaClienti = clienteService.findAll();
//		
//		Response response = RestAssured.given()
//                .when()
//                .get("clienti")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .extract()
//                .response();
//		
//		List<Cliente> listaAct = response.getBody().jsonPath().getList("", Cliente.class);
//		
//		assertEquals(listaClienti.size(), listaAct.size());
//		
//		clienteService.delete(c1);
//		clienteService.delete(c2);
//		
//		Response responseListaVuota = RestAssured.given()
//                .when()
//                .get("clienti")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .extract()
//                .response();
//		
//		assertEquals("\"Nessun cliente presente\"", responseListaVuota.getBody().asString());
//		
//		clienteService.save(c1);
//		clienteService.save(c2);
//	}
//	
//	@Test
//	void lockUnlockTestClienteBloccato() {
//		
//		RestAssured.given().param("email", c2.getEmail()).when().post("lockUnlock").then().statusCode(200);
//		Optional<Cliente> clienteOpt = clienteService.findById(c2.getEmail());
//		assertFalse(clienteOpt.get().isBlocked());
////		
////		RestAssured.given().param("email", c2.getEmail()).when().post("lockUnlock").then().statusCode(200);
////		clienteOpt = clienteService.findById(c2.getEmail());
////		assertTrue(clienteOpt.get().isBlocked());
//	}
//	    
//	@AfterAll
//	void destroy() {
//		clienteService.delete(c1);
//		clienteService.delete(c2);
//		invoiceService.delete(inv1);
//		invoiceService.delete(inv2);
//		invoiceService.delete(inv3);
//		cryptoService.delete(test1);
//		cryptoService.delete(test2);
//	}
//}
