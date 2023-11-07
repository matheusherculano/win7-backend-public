package com.gestor.app.phone3cx.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class Phone3cxCustomRepository {
	
	@PersistenceContext(unitName = "phone3cxEntityManager")
    private EntityManager entityManager;

	public Long obterMetricaLigacoesPeriodoPersonalizado(String ramalCliente, String filtro) {
		String filtroDatas = "";
		if(!filtro.equals("")) {
			filtroDatas = " AND c.dtAtual "+filtro;
		}
		
		String sql = "SELECT COUNT(answered) "
				+ "FROM ( "
				+ "    SELECT answered "
				+ "    FROM cl_get_call_log_win7(:periodoInicial, :periodoFinal, 0, '' , 2, :ramalCliente, 0, 0, NULL, NULL, true) "
				+ ") AS subquery_alias WHERE answered = true ";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        
        query.setParameter("idCliente", ramalCliente);
//        query.setParameter("tipo", tipoMetrica);
        
        
        return query.getSingleResult();
    }
}
