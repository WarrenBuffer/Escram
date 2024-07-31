package com.escram.escrow.utils;

public interface Costanti {	
	String COIN_REMITTER_URL = "https://coinremitter.com/api/v3/";
	String TCN_API_KEY = "$2b$10$OJFGvNBLVCrvKMOs0eHMBe.RT35stJlmoIobd18PXljqWmh5Q0KQG";
	String TCN_ADDRESS = "nqZ2jgsrTiWqFMvcGvzTSyW73rdvcjt7mi";
	String TCN_PASSWORD = "Password01";
	int INVOICE_EXPIRATION = 240; // MAX di coinremitter
	// TODO: Cambiare gli url quando il server sarà pubblico
	String NOTIFY_URL = ""; // "http://localhost:8080/cliente/notify";
	String SUCCESS_URL = ""; // "http://localhost:8080/cliente/success";
	String FAIL_URL = ""; // "http://localhost:8080/cliente/fail";
}
