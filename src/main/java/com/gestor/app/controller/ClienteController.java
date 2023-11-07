package com.gestor.app.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.app.DTO.MetricasDTO;
import com.gestor.app.DTO.RequestMetricsDTO;
import com.gestor.app.oldPhpApp.model.TblCliente;
import com.gestor.app.service.ClienteService;
import com.gestor.app.service.MetricasService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private MetricasService metricasService;
	
	@RequestMapping(value = "/get-all", method = RequestMethod.GET)
	public ResponseEntity<List<TblCliente>> getAll() {
		List<TblCliente> list = clienteService.getAllClientes();
		return new ResponseEntity(list,HttpStatus.OK);
	}

	@RequestMapping(value = "/get/{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<TblCliente> get(@PathVariable Long idCliente) {
		
		TblCliente cliente = clienteService.getById(idCliente);
		
		return new ResponseEntity<TblCliente>(cliente, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/metrics", method = RequestMethod.POST)
	public ResponseEntity getMetrics(@RequestBody RequestMetricsDTO requestMetricsDTO) throws IOException, JRException {
		
		MetricasDTO dto = metricasService.obterMetricasByCliente(requestMetricsDTO);

		return new ResponseEntity(dto, HttpStatus.OK);
	}

}
