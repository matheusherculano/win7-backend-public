package com.gestor.app.service;

import java.util.List;

import com.gestor.app.DTO.UsuarioDTO;
import com.gestor.app.model.Usuario;

public interface UsuarioService {

	void atualizarDataHoraUltimoAcesso(Usuario usuario);
	
	void cadastrarOrCriarusuario(UsuarioDTO dto) throws Exception;

	void excluirUsuario(Long id);

	Usuario findByUsuarioAndByAmbiente(String nomeUsuario, String ambiente);

	List<UsuarioDTO> findAllUsuariosByAmbiente();
	
	
	
}
