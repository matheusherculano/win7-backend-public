package com.gestor.app.oldPhpApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestor.app.oldPhpApp.model.TblLead;

@Repository
public interface TblLeadRepository extends JpaRepository<TblLead, Long> {

	@Query("SELECT COUNT(c.leadTipo) FROM TblLead c WHERE c.tblCliente.id = :idCliente "
			+ "AND c.nuLixeira = 0"
			+ "AND c.leadTipo = :tipo")
    Long countByTipo(@Param("idCliente") Long idCliente, @Param("tipo") String tipo);
}