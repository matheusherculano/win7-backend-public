package com.gestor.app.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestor.app.phone3cx.repository.Phone3cxRepository;
import com.gestor.app.service.Phone3cxService;

@Service
public class Phone3cxServiceImpl implements Phone3cxService{

	@Autowired
	private Phone3cxRepository phone3cxCustomRepository;

	public Long obterQtdLigacoesByCliente(LocalDate periodoInicial, LocalDate periodoFinal, String ramalCliente) {
		return phone3cxCustomRepository.obterQtdLigacoesByCliente(periodoInicial, periodoFinal,
				ajustarRamal(ramalCliente));
	}

	private String ajustarRamal(String ramal) {
		int ramal_tipo = ramal.length() >= 8 ? 4 : 2;
		return ramal_tipo == 4 ? "%" + ramal + "%" : ramal;
	}

}
