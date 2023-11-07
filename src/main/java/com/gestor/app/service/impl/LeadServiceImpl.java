package com.gestor.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestor.app.DTO.MetricasDTO;
import com.gestor.app.oldPhpApp.repository.TblLeadRepository;
import com.gestor.app.service.LeadService;

@Service
public class LeadServiceImpl implements LeadService{
	
	@Autowired
	private TblLeadRepository tblLeadRepository;

	@Override //tipo esta no db ex ('Formulário', 'WhatsApp')
	public Long obterMetricas(Long clienteId, String tipo, String filtro) {
		return tblLeadRepository.countByTipo(clienteId, tipo);
	}

}
