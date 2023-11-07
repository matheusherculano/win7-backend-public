package com.gestor.app.service;

import com.gestor.app.DTO.MetricasDTO;

public interface LeadService {
	
	Long obterMetricas(Long clienteId, String tipo, String filtro);
}
