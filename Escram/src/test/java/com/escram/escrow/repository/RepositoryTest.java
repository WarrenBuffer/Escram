package com.escram.escrow.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
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

import com.escram.escrow.businesscomponent.model.Cliente;
import com.escram.escrow.businesscomponent.model.Crypto;
import com.escram.escrow.businesscomponent.model.Invoice;
import com.escram.escrow.businesscomponent.model.Notifica;
import com.escram.escrow.businesscomponent.model.Portafoglio;
import com.escram.escrow.businesscomponent.model.Transazione;
import com.escram.escrow.businesscomponent.model.enums.StatoTransazione;
import com.escram.escrow.businesscomponent.model.enums.TipoTransazione;
import com.escram.escrow.businesscomponent.model.enums.TipologiaCliente;
import com.escram.escrow.service.ClienteService;
import com.escram.escrow.service.CryptoService;
import com.escram.escrow.service.InvoiceService;
import com.escram.escrow.service.NotificaService;
import com.escram.escrow.service.PortafoglioService;
import com.escram.escrow.service.TransazioneService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class RepositoryTest {
	
	@Inject
	InvoiceService invoiceService;
	@Inject
	ClienteService clienteService;
	@Inject
	CryptoService cryptoService;
	@Inject
	NotificaService notificaService;
	@Inject
	PortafoglioService portafoglioService;
	@Inject
	TransazioneService transazioneService;
	
	private Invoice inv1 = new Invoice();
	private Invoice inv2 = new Invoice();
	private Invoice inv3 = new Invoice();
	private Crypto test1 = new Crypto();
	private Crypto test2 = new Crypto();
	private Cliente c1 = new Cliente();
	private Cliente c2 = new Cliente();
	private Notifica n1 = new Notifica();
	private Notifica n2 = new Notifica();
	private Portafoglio p1 = new Portafoglio();
	private Portafoglio p2 = new Portafoglio();
	private Transazione t1 = new Transazione();
	private Transazione t2 = new Transazione();
	private Transazione t3 = new Transazione();
	
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
		c1.setTipologia(TipologiaCliente.COMPRATORE);
		
		c2.setNome("Paolo");
		c2.setCognome("Verdi");
		c2.setEmail("email2@email.com");
		c2.setPassword("pass");
		c2.setBlocked(true);
		c2.setTipologia(TipologiaCliente.VENDITORE);
		
		n1.setEmailCliente("email2@email.com");
		n1.setInvoiceId("3");
		
		n1.setEmailCliente("email2@email.com");
		n1.setInvoiceId("2");
		
		p1.setBlocked(false);
		p1.setCreazione(new Date());
		p1.setScadenza(new Date());
		p1.setCrypto(test1);
		p1.setEmailCliente(c2.getEmail());
		p1.setIndirizzo("INDIRIZZOTEST1");
		p1.setQrCode("QRTEST1");
		p1.setSaldo(1000);
		p1.setTransazioni(new HashSet<Transazione>());
		
		p2.setBlocked(false);
		p2.setCreazione(new Date());
		p2.setScadenza(new Date());
		p2.setCrypto(test2);
		p2.setEmailCliente(c1.getEmail());
		p2.setIndirizzo("INDIRIZZOTEST2");
		p2.setQrCode("QRTEST2");
		p2.setSaldo(1000);
		p2.setTransazioni(new HashSet<Transazione>());
		
		t1.setAmount(100);
		t1.setDate(new Date());
		t1.setFees(5);
		t1.setId("1");
		t1.setTipo(TipoTransazione.PRELIEVO);
		t1.setToAddress("Test1");
		t1.setTxId("1");
		
		t2.setAmount(400);
		t2.setDate(new Date());
		t2.setFees(5);
		t2.setId("2");
		t2.setTipo(TipoTransazione.DEPOSITO);
		t2.setToAddress("Test2");
		t2.setTxId("2");
		
		t3.setAmount(100);
		t3.setDate(new Date());
		t3.setFees(5);
		t3.setId("3");
		t3.setTipo(TipoTransazione.PRELIEVO);
		t3.setToAddress("Test3");
		t3.setTxId("3");	
		
		c1 = clienteService.save(c1);
		c2 = clienteService.save(c2);
		test1 = cryptoService.save(test1);
		test2 = cryptoService.save(test2);
		inv1 = invoiceService.save(inv1);
		inv2 = invoiceService.save(inv2);
		inv3 = invoiceService.save(inv3);
		n1 = notificaService.save(n1);
		n2 = notificaService.save(n2);
		p1 = portafoglioService.save(p1);
		p2 = portafoglioService.save(p2);
		t1 = transazioneService.save(t1);
		t2 = transazioneService.save(t2);
		t3 = transazioneService.save(t3);
	}
	
	@Test
	void clienteRepositoryTest() {
		List<Cliente> listaClienti = clienteService.findAll();
		assertNotEquals(0, listaClienti.size());
		assertFalse(listaClienti.isEmpty());
		Cliente a = new Cliente();
		a.setNome("Paola");
		a.setCognome("Viola");
		a.setEmail("emailTest@email.it");
		a.setPassword("pass");
		a.setBlocked(false);
		a.setTipologia(TipologiaCliente.COMPRATORE);
		Cliente testSave = clienteService.save(a);
		assertEquals(a.getEmail(), testSave.getEmail());
		Optional<Cliente> clienteOptTest = clienteService.findById(a.getEmail());
		assertTrue(clienteOptTest.isPresent());
		clienteService.delete(a);
	}
	
	@Test
	void cryptoRepositoryTest() {
		List<Crypto> listaCrypto = cryptoService.findAll();
		assertNotEquals(0, listaCrypto.size());
		assertFalse(listaCrypto.isEmpty());
		Crypto a = new Crypto();
		a.setSimbolo("TST");
		a.setNome("TSTCRP");
		a.setUrlImmagine("TESTURLCRP");
		Crypto testSave = cryptoService.save(a);
		assertEquals(a.getSimbolo(), testSave.getSimbolo());
		Optional<Crypto> cryptoOptTest = cryptoService.findById(a.getSimbolo());
		assertTrue(cryptoOptTest.isPresent());
		cryptoService.delete(a);
	}
	
	@Test
	void invoiceRepositoryTest() {
		List<Invoice> listaInvoice = invoiceService.findAll();
		assertNotEquals(0, listaInvoice.size());
		assertFalse(listaInvoice.isEmpty());
		Invoice a = new Invoice();
		a.setStatoDst(StatoTransazione.CONFERMATO);
		a.setStatoSrc(StatoTransazione.ANNULLATO);
		a.setDataApertura(new Date());
		a.setDataScadenza(new Date());
		a.setEmailDst("email@email.it");
		a.setEmailSrc("email2@email.com");
		a.setId("4");
		a.setUrl("urltest4");
		a.setInvoiceId("INV4");
		a.setStatus("Confermato");
		a.setUsdAmount(350);
		Invoice testSave = invoiceService.save(a);
		assertEquals(a.getInvoiceId(), testSave.getInvoiceId());
		Optional<Invoice> invoiceOptTest = invoiceService.findById(a.getId());
		assertTrue(invoiceOptTest.isPresent());
	}
	
	@Test
	void notificaRepositoryTest() {
		List<Notifica> listaNotifiche = notificaService.findAll();
		assertNotEquals(0, listaNotifiche.size());
		assertFalse(listaNotifiche.isEmpty());
		Notifica a = new Notifica();
		a.setEmailCliente("email2@email.com");
		a.setInvoiceId("1");
		Notifica testSave = notificaService.save(a);
		assertEquals(a.getId(), testSave.getId());
		Optional<Notifica> notificaOptTest = notificaService.findById(a.getId());
		assertTrue(notificaOptTest.isPresent());
	}
	
	@Test
	void portafoglioRepositoryTest() {
		List<Portafoglio> listaPortafogli = portafoglioService.findAll();
		assertNotEquals(0, listaPortafogli.size());
		assertFalse(listaPortafogli.isEmpty());
		Portafoglio a = new Portafoglio();
		a.setBlocked(false);
		a.setCreazione(new Date());
		a.setScadenza(new Date());
		a.setCrypto(test1);
		a.setEmailCliente(c1.getEmail());
		a.setIndirizzo("INDIRIZZOTEST3");
		a.setQrCode("QRTEST3");
		a.setSaldo(5000);
		a.setTransazioni(new HashSet<Transazione>());
		Portafoglio testSave = portafoglioService.save(a);
		assertEquals(a.getIndirizzo(), testSave.getIndirizzo());
		Optional<Portafoglio> portafoglioOptTest = portafoglioService.findById(a.getIndirizzo());
		assertTrue(portafoglioOptTest.isPresent());
	}
	
	@Test
	void transazioneRepositoryTest() {
		List<Transazione> listaT = transazioneService.findAll();
		assertNotEquals(0, listaT.size());
		assertFalse(listaT.isEmpty());
		Transazione a = new Transazione();
		a.setAmount(50);
		a.setDate(new Date());
		a.setFees(5);
		a.setId("3");
		a.setTipo(TipoTransazione.DEPOSITO);
		a.setToAddress("Test3");
		a.setTxId("3");
		Transazione testSave = transazioneService.save(a);
		assertEquals(a.getId(), testSave.getId());
		Optional<Transazione> transazioneOptTest = transazioneService.findById(a.getId());
		assertTrue(transazioneOptTest.isPresent());
	}
	
	@AfterAll
	void tearDown() {
		transazioneService.delete(t1);
		transazioneService.delete(t2);
		transazioneService.delete(t3);
		invoiceService.delete(inv1);
		invoiceService.delete(inv2);
		invoiceService.delete(inv3);
		notificaService.delete(n1);
		notificaService.delete(n2);
		portafoglioService.delete(p1);
		portafoglioService.delete(p2);
		clienteService.delete(c1);
		clienteService.delete(c2);
		cryptoService.delete(test1);
		cryptoService.delete(test2);
	}

}
