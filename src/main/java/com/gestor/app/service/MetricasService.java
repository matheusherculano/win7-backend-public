package com.gestor.app.service;

import java.io.IOException;

import com.gestor.app.DTO.MetricasDTO;
import com.gestor.app.DTO.RequestMetricsDTO;


public interface MetricasService {
	
	MetricasDTO obterMetricasByCliente(RequestMetricsDTO requestDTO)throws IOException;
}
