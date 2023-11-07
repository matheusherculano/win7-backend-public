package com.gestor.app.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestor.app.oldPhpApp.model.TblCliente;
import com.gestor.app.oldPhpApp.repository.TblClienteRepository;
import com.gestor.app.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private TblClienteRepository tblClienteRepository;
	
	@Override
	public List<TblCliente> getAllClientes() {
		return tblClienteRepository.findAll();
	}

	@Override
	public TblCliente getById(Long id) {
		TblCliente cliente = tblClienteRepository.findByIdTblCliente(id);
		return cliente;
	}
}
