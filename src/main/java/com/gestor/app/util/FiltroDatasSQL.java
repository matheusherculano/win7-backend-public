package com.gestor.app.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.gestor.app.DTO.DateRangeDTO;

public class FiltroDatasSQL {
	public static String filterDateWithBetween(String valor, LocalDate dataInicial, LocalDate dataFinal) {
		String str = "";
		LocalDate startWeek = null;
		LocalDate endWeek = null;
		LocalDate dtInicial = null;
		LocalDate dtFinal = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		switch (valor) {
		case "ALL_TIME":
			str = "";
			break;
		case "TODAY":
			LocalDate dataHj = LocalDate.now();
			str = " = '" + dataHj.format(formatter) + "'";
			break;
		case "YESTERDAY":
			LocalDate dataOntem = LocalDate.now().minusDays(1);
			str = " = '" + dataOntem.format(formatter) + "'";
			break;
		case "LAST_7_DAYS":
			startWeek = LocalDate.now().minusDays(7);
			endWeek = LocalDate.now().minusDays(1);
			str = " BETWEEN '" + startWeek.format(DateTimeFormatter.ISO_DATE) + "' AND '"
					+ endWeek.format(DateTimeFormatter.ISO_DATE) + "'";
			break;
		case "LAST_BUSINESS_WEEK":
			LocalDate previousWeek = LocalDate.now().minusWeeks(1).plusDays(1);
			startWeek = previousWeek.with(java.time.DayOfWeek.MONDAY);
			endWeek = startWeek.plusDays(4);
			str = " BETWEEN '" + startWeek.format(DateTimeFormatter.ISO_DATE) + "' AND '"
					+ endWeek.format(DateTimeFormatter.ISO_DATE) + "'";
			break;
		case "LAST_14_DAYS":
			startWeek = LocalDate.now().minusDays(14);
			endWeek = LocalDate.now().minusDays(1);
			str = " BETWEEN '" + startWeek.format(DateTimeFormatter.ISO_DATE) + "' AND '"
					+ endWeek.format(DateTimeFormatter.ISO_DATE) + "'";
			break;
		case "LAST_30_DAYS":
			startWeek = LocalDate.now().minusDays(30);
			endWeek = LocalDate.now().minusDays(1);
			str = " BETWEEN '" + startWeek.format(DateTimeFormatter.ISO_DATE) + "' AND '"
					+ endWeek.format(DateTimeFormatter.ISO_DATE) + "'";
			break;
		case "THIS_MONTH":
			LocalDate dataAtual = LocalDate.now();
			dtInicial = dataAtual.withDayOfMonth(1);
			dtFinal = dataAtual;
			str = " BETWEEN '" + dtInicial.format(DateTimeFormatter.ISO_DATE) + "' AND '"
					+ dtFinal.format(DateTimeFormatter.ISO_DATE) + "'";
			break;
		case "LAST_MONTH":
			dtInicial = LocalDate.now().minusMonths(1).withDayOfMonth(1);
			dtFinal = LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
			str = " BETWEEN '" + dtInicial.format(DateTimeFormatter.ISO_DATE) + "' AND '"
					+ dtFinal.format(DateTimeFormatter.ISO_DATE) + "'";
			break;
		case "PERSONALIZADO":
			dtInicial = dataInicial;
			dtFinal = dataFinal;
			str = " BETWEEN '" + dtInicial.format(DateTimeFormatter.ISO_DATE) + "' AND '"
					+ dtFinal.format(DateTimeFormatter.ISO_DATE) + "'";
			break;
		default:
			str = " = " + LocalDate.now().format(DateTimeFormatter.ISO_DATE);
			break;

		}
		return str;
	}

	public static DateRangeDTO filterDateWithStartEnd(String option) {
		LocalDate dataInicial;
		LocalDate dataFinal = LocalDate.now();
		long qtdDias = 1;

		switch (option) {
		case "ALL_TIME":
			dataInicial = dataFinal.minusYears(200L); // TODO
			break;
		case "TODAY":
			dataInicial = dataFinal;
			break;
		case "YESTERDAY":
			dataInicial = dataFinal.minusDays(1);
			dataFinal = dataFinal.minusDays(1);
			break;
		case "LAST_7_DAYS":
			dataInicial = dataFinal.minusDays(7);
			dataFinal = dataFinal.minusDays(1);
			qtdDias = calcularQuantidadeDias(dataInicial, dataFinal);
			break;
		case "LAST_BUSINESS_WEEK":
			dataInicial = findPreviousBusinessWeekStartDate(dataFinal);
			qtdDias = calcularQuantidadeDias(dataInicial, dataFinal);
			break;
		case "LAST_14_DAYS":
			dataInicial = dataFinal.minusDays(14);
			dataFinal = dataFinal.minusDays(1);
			qtdDias = calcularQuantidadeDias(dataInicial, dataFinal);
			break;
		case "LAST_30_DAYS":
			dataInicial = dataFinal.minusDays(30);
			dataFinal = dataFinal.minusDays(1);
			qtdDias = calcularQuantidadeDias(dataInicial, dataFinal);
			break;
		case "THIS_MONTH":
			dataInicial = dataFinal.withDayOfMonth(1);
			qtdDias = calcularQuantidadeDias(dataInicial, dataFinal);
			break;
		case "LAST_MONTH":
			dataInicial = dataFinal.withDayOfMonth(1).minusMonths(1);
			dataFinal = dataFinal.withDayOfMonth(1).minusDays(1);
			qtdDias = calcularQuantidadeDias(dataInicial, dataFinal);
			break;
		default:
			throw new IllegalArgumentException("Opção inválida: " + option);
		}

		return new DateRangeDTO(dataInicial, dataFinal, qtdDias);
	}

	public static long calcularQuantidadeDias(LocalDate dataInicial, LocalDate dataFinal) {
		return ChronoUnit.DAYS.between(dataInicial, dataFinal) + 1;
	}

	private static LocalDate findPreviousBusinessWeekStartDate(LocalDate date) {
		LocalDate previousWeekStartDate = date.minusDays(7);
		while (previousWeekStartDate.getDayOfWeek() == DayOfWeek.SATURDAY
				|| previousWeekStartDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
			previousWeekStartDate = previousWeekStartDate.minusDays(1);
		}
		return previousWeekStartDate;
	}

}