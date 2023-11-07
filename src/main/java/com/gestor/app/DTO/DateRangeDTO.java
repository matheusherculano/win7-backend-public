package com.gestor.app.DTO;

import java.time.LocalDate;

public class DateRangeDTO {
	
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private long qtdDias;

	public DateRangeDTO(LocalDate dataInicial, LocalDate dataFinal, long qtdDias) {
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.qtdDias = qtdDias;
	}
	
	
	public long getQtdDias() {
		return qtdDias;
	}


	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}
}
