package com.gestor.app.oldPhpApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestor.app.model.Usuario;
import com.gestor.app.oldPhpApp.model.TblCliente;

@Repository
public interface TblClienteRepository extends JpaRepository<TblCliente, Long>{
	
	@Query("SELECT u FROM TblCliente u WHERE u.id = :idCliente")
	TblCliente findByIdTblCliente(@Param("idCliente") Long idCliente);
	
}
