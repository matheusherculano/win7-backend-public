package com.gestor.app.oldPhpApp.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class MetricasRepositoryCustom {

	@PersistenceContext(unitName = "phpEntityManager")
    private EntityManager entityManager;
	
//	@Query("SELECT COUNT(c.leadTipo) FROM TblLead c WHERE c.tblCliente.id = :idCliente "
//			+ "AND c.nuLixeira = 0"
	
	public Long obterMetricas(Long idCliente, String tipoMetrica, String filtro) {
		String filtroDatas = "";
		if(!filtro.equals("")) {
			filtroDatas = " AND c.dtAtual "+filtro;
		}
		
		String sql = "SELECT COUNT(c.leadTipo) FROM TblLead c WHERE c.tblCliente.id = :idCliente "
				+ "AND c.nuLixeira = 0 "
				+ "AND c.leadTipo = :tipo "+filtroDatas;
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        
        query.setParameter("idCliente", idCliente);
        query.setParameter("tipo", tipoMetrica);
        
        
        return query.getSingleResult();
    }
}
