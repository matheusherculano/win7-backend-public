package com.gestor.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestor.app.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u JOIN u.ambiente a WHERE u.login = :login AND a.nomeAcesso = :ambiente")
	Usuario findByUsuarioAndByAmbiente(@Param("login") String login, @Param("ambiente")String ambiente);

	@Query("SELECT u FROM Usuario u JOIN u.ambiente a WHERE a.nomeAcesso = :ambiente")
	List<Usuario> findAllUsuariosByAmbiente(@Param("ambiente")String ambiente);
}
