package com.gestor.app.service;

import java.time.LocalDate;

public interface Phone3cxService {

	Long obterQtdLigacoesByCliente(LocalDate periodoInicial, LocalDate periodoFinal, String ramalCliente);
}
