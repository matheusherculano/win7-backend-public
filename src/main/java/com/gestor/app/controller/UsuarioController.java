package com.gestor.app.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.app.DTO.UsuarioDTO;
import com.gestor.app.model.Usuario;
import com.gestor.app.oldPhpApp.repository.TblClienteRepository;
import com.gestor.app.service.UsuarioService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/usuario")
public class UsuarioController{

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private TblClienteRepository repo;
	
	@RequestMapping(value = "/get/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Long> get(@PathVariable Long idUsuario) {
		return new ResponseEntity<Long>(idUsuario, HttpStatus.OK);
	}

	@RequestMapping(value = "/all-in-ambiente", method = RequestMethod.GET)
	public ResponseEntity<List<UsuarioDTO>> getAll() {
		List<UsuarioDTO> list = service.findAllUsuariosByAmbiente();
		return new ResponseEntity<List<UsuarioDTO>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "delete/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity removerUsuario(@PathVariable Long idUsuario) {
		service.excluirUsuario(idUsuario);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveOrUpdate(@RequestBody UsuarioDTO dto) {
		try {
			service.cadastrarOrCriarusuario(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
        return new ResponseEntity(HttpStatus.OK);
    }
}

