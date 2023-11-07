package com.gestor.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestor.app.oldPhpApp.model.TblCliente;

public interface ClienteService {
	List<TblCliente> getAllClientes();
	
	TblCliente getById(Long id);
}
