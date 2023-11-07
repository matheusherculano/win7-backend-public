package com.gestor.app.phone3cx.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestor.app.phone3xc.model.Cl_calls;

@Repository
public interface Phone3cxRepository extends JpaRepository<Cl_calls, Long>{

	@Query(value = "SELECT COUNT(answered) "
			+ "FROM ( "
			+ "    SELECT answered "
			+ "    FROM cl_get_call_log_win7(:periodoInicial, :periodoFinal, 0, '' , 2, :ramalCliente, 0, 0, NULL, NULL, true) "
			+ ") AS subquery_alias WHERE answered = true", nativeQuery = true)
	Long obterQtdLigacoesByCliente(@Param("periodoInicial") LocalDate periodoInicial, @Param("periodoFinal") LocalDate periodoFinal, @Param("ramalCliente") String ramalCliente);

}
