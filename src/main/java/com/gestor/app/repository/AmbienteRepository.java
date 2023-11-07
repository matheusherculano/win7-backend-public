package com.gestor.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestor.app.model.Ambiente;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Long>{
	
	Ambiente findByNomeAcesso(String nomeAcesso);

}
