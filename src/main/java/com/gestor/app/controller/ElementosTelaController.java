package com.gestor.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.app.enums.PeriodoAds;
import com.gestor.app.util.SelectOption;

@RestController
@RequestMapping("/elementos-tela")
public class ElementosTelaController {
	
	@RequestMapping(value = "/periodo-ads", method = RequestMethod.GET)
	public ResponseEntity<List<SelectOption>> getAll() {
		List<SelectOption> selectOptions = new ArrayList<>();
		
		for(PeriodoAds item : PeriodoAds.values()) {
			selectOptions.add(new SelectOption(item.getName(), item.getDescription()));
		}
		
		return new ResponseEntity(selectOptions ,HttpStatus.OK);
	}
}
