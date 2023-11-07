package com.gestor.app.controller;

import java.io.IOException;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.app.DTO.TesteDTO;
import com.gestor.app.oldPhpApp.repository.MetricasRepositoryCustom;
import com.gestor.app.phone3cx.repository.Phone3cxRepository;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/testes")
public class TestesInternosController {

	@Autowired
	private Phone3cxRepository phone3cxCustomRepository;

	@Autowired
	private MetricasRepositoryCustom metricasRepositoryCustom;


	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public ResponseEntity testes(HttpServletResponse response) throws IOException, JRException {
//		Long l = phone3cxCustomRepository.obterQtdLigacoesByCliente("2023-07-16", "2023-07-16", "8048");
//		phone3cxCustomRepository.obterQtdLigacoesByCliente(LocalDate.of(2023, 7, 16), LocalDate.of(2023, 7, 23), "8048");
		
		return new ResponseEntity("Deu tudo certo! servidor em pé", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/2", method = RequestMethod.POST)
	public ResponseEntity testes2(@RequestBody TesteDTO dto) throws IOException, JRException {
//		AdsClienteDTO ret = adsCampaingService.getMetricasAllCampaings(3613787017L);
		
		Long l = metricasRepositoryCustom.obterMetricas(449L, "WhatsApp", null);

		return new ResponseEntity(l, HttpStatus.OK);
	}

}
