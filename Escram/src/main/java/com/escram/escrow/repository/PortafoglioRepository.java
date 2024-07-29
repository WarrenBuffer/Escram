package com.escram.escrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escram.escrow.businesscomponent.model.Portafoglio;

@Repository
public interface PortafoglioRepository extends JpaRepository<Portafoglio, String>{
	@Query(value="select * from portafoglio where id_cliente=?1", nativeQuery = true)
	List<Portafoglio> findAllByIdCliente(long idCliente);
}
