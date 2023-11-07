package com.gestor.app.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gestor.app.enums.PeriodoAds;

public class RequestMetricsDTO {

	private Long clienteId;
	private Long adwords;
	private PeriodoAds periodoAds;
	private LocalDate periodoInicial; 
	private LocalDate periodoFinal;
	
	public RequestMetricsDTO() {
		// TODO Auto-generated constructor stub
	}

	
	public LocalDate getPeriodoInicial() {
		return periodoInicial;
	}


	public void setPeriodoInicial(LocalDate periodoInicial) {
		this.periodoInicial = periodoInicial;
	}


	public LocalDate getPeriodoFinal() {
		return periodoFinal;
	}


	public void setPeriodoFinal(LocalDate periodoFinal) {
		this.periodoFinal = periodoFinal;
	}


	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getAdwords() {
		return adwords;
	}

	public void setAdwords(Long adwords) {
		this.adwords = adwords;
	}

	public PeriodoAds getPeriodoAds() {
		return periodoAds;
	}

	public void setPeriodoAds(PeriodoAds periodoAds) {
		this.periodoAds = periodoAds;
	}
	

}
