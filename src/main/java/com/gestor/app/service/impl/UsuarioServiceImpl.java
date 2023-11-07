package com.gestor.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestor.app.DTO.UsuarioDTO;
import com.gestor.app.model.Ambiente;
import com.gestor.app.model.Usuario;
import com.gestor.app.repository.AmbienteRepository;
import com.gestor.app.repository.UsuarioRepository;
import com.gestor.app.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AmbienteRepository ambienteRepository;
	
	private BCryptPasswordEncoder passowrdEncoder() {
		return new BCryptPasswordEncoder();
	}


	private List<UsuarioDTO> usuarioToDTOList(List<Usuario> usuarioList) {
		List<UsuarioDTO> listDTO = new ArrayList<UsuarioDTO>();
		usuarioList.forEach(usuario ->{
			UsuarioDTO dto = usuarioToDTO(usuario);
			listDTO.add(dto);
		});
		return listDTO;
	}

	private UsuarioDTO usuarioToDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO(
				usuario.getId(), 
				usuario.getNome(), 
				usuario.getLogin(),
				usuario.getSenha(),
				usuario.getEmail(),
				usuario.getWhatsapp(),
				usuario.getTimeStamp(),
				usuario.getAmbiente(),
				usuario.getRoles());
		return dto;
	}
	

	@Override
	public void cadastrarOrCriarusuario(UsuarioDTO dto) throws Exception {
		if(dto.id() == null) {
			cadastrarUsuario(dto);
		}else {
			editarUsuario(dto);
		}
		
	}
	
	private void cadastrarUsuario(UsuarioDTO dto) throws Exception {
		String nomeUsuario = dto.login();
		Ambiente ambiente = getAmbienteLogado();
		
		if(ambiente == null) {
			throw new Exception("Ambiente não existente");
		}
		
		boolean usuarioExiste = usuarioRepository.findByUsuarioAndByAmbiente(nomeUsuario, ambiente.getNomeAcesso()) == null ? false : true;
		
		if(usuarioExiste) {
			throw new Exception("Usuário já cadastrado");
		}else {
			Usuario usuario = new Usuario(dto);
			usuario.setSenha(passowrdEncoder().encode(usuario.getSenha()));
			usuario.setAmbiente(ambiente);
			usuario.setTimeStamp(LocalDateTime.now());
			
			usuarioRepository.save(usuario);
		}
	}
	
	private void editarUsuario(UsuarioDTO dto) throws Exception {
		Ambiente ambiente = getAmbienteLogado();
		
		Usuario usuario = new Usuario(dto);
		usuario.setAmbiente(ambiente);
		usuario.setTimeStamp(LocalDateTime.now());
		
		if(usuario.getSenha() != null) { // caso app tenha alterado a senha
			usuario.setSenha(passowrdEncoder().encode(usuario.getSenha()));
		}else { 
			// se a senha vier null, salvo com a senha atual
			Usuario temp = usuarioRepository.getById(dto.id());
			usuario.setSenha(temp.getSenha());
		}
		
		usuarioRepository.save(usuario);
	}


	private Ambiente getAmbienteLogado() {
		String nomeAmbiente = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
		Ambiente ambiente = ambienteRepository.findByNomeAcesso(nomeAmbiente);
		return ambiente;
	}

	@Override
	public Usuario findByUsuarioAndByAmbiente(String nomeUsuario, String ambiente) {
		Usuario usuario =  usuarioRepository.findByUsuarioAndByAmbiente(nomeUsuario.toLowerCase(), ambiente.toLowerCase());
		return usuario;
	}

	@Override
	public void atualizarDataHoraUltimoAcesso(Usuario usuario) {
		usuario.setTimeStamp(LocalDateTime.now());
		usuarioRepository.save(usuario);
		
	}

	@Override
	public List<UsuarioDTO> findAllUsuariosByAmbiente() {
		String nomeAmbiente = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
		return usuarioToDTOList(usuarioRepository.findAllUsuariosByAmbiente(nomeAmbiente));
	}


	@Override
	public void excluirUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

}
