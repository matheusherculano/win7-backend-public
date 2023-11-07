package com.gestor.app.google.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestor.app.DTO.AdsClienteDTO;
import com.gestor.app.enums.PeriodoAds;
import com.google.ads.googleads.v14.services.GoogleAdsRow;
import com.google.ads.googleads.v14.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v14.services.SearchGoogleAdsStreamRequest;
import com.google.ads.googleads.v14.services.SearchGoogleAdsStreamResponse;
import com.google.api.gax.rpc.ServerStream;

@Service
public class AdsCampaingService {

	@Autowired
	private GoogleAdsClientService adsClientService;

	// obter as metricas de um cliente somando todas as camapanhas
	public AdsClienteDTO getMetricasAllCampaingsPeriodoPersonalizado(long customerId, LocalDate periodoInicial,
			LocalDate periodoFinal) throws IOException {
		return getMetricasAllCampaings(customerId, null, periodoInicial, periodoFinal);
	}

	// obter as metricas de um cliente somando todas as camapanhas
	public AdsClienteDTO getMetricasAllCampaingsPeriodoAds(long customerId, PeriodoAds periodo) throws IOException {
		return getMetricasAllCampaings(customerId, periodo, null, null);
	}

	private AdsClienteDTO getMetricasAllCampaings(long customerId, PeriodoAds periodo, LocalDate periodoInicial,
			LocalDate periodoFinal) throws IOException {

		AdsClienteDTO dto = null;

		try (GoogleAdsServiceClient googleAdsServiceClient = adsClientService.get().getLatestVersion()
				.createGoogleAdsServiceClient()) {

			// monta a consulta de acordo com o tipo de periodo pesquisado
			String query = montarQueryPorPeriodo(periodo, periodoInicial, periodoFinal);

			// Constructs the SearchGoogleAdsStreamRequest.
			SearchGoogleAdsStreamRequest request = SearchGoogleAdsStreamRequest.newBuilder()
					.setCustomerId(Long.toString(customerId)).setQuery(query).build();

			// Creates and issues a search Google Ads stream request that will retrieve all
			// campaigns.
			ServerStream<SearchGoogleAdsStreamResponse> stream = googleAdsServiceClient.searchStreamCallable()
					.call(request);

			Long invalidClicks = 0L;
			Long clicks = 0L;
			Long impression = 0L; // quantidade de anuncios
			Long costMicros = 0L;
			Double ctr = 0.0;
			int googleAdsRowsSize = 0;
			// Iterates through and prints all of the results in the stream response.
			for (SearchGoogleAdsStreamResponse response : stream) {
				googleAdsRowsSize = response.getResultsList().size();
				for (GoogleAdsRow googleAdsRow : response.getResultsList()) {
//		          googleAdsRow.getCampaign().getStatus();

					invalidClicks = invalidClicks + googleAdsRow.getMetrics().getInvalidClicks();
					clicks = clicks + googleAdsRow.getMetrics().getClicks(); // acesso ao site
					impression = impression + googleAdsRow.getMetrics().getImpressions(); // quantidade de anuncios
					costMicros = costMicros + googleAdsRow.getMetrics().getCostMicros();
					ctr = ctr + googleAdsRow.getMetrics().getCtr();

				}
			}

			DecimalFormat decimalformat = new DecimalFormat("#.00"); // deixar 2 casas decimais

			double costMicrosTotal = (double) costMicros / 1_000_000; // custo total
			double totalCpc = 0.00;
			if (clicks > 0) {
				totalCpc = (double) costMicros / clicks / 1_000_000; // CUSTO POR CLIQUE (CPC)
			}

			dto = new AdsClienteDTO(invalidClicks, clicks, impression, costMicrosTotal, totalCpc, ctr, googleAdsRowsSize);
		}

		return dto;
	}

	private String montarQueryPorPeriodo(PeriodoAds periodo, LocalDate periodoInicial, LocalDate periodoFinal) {
		String query = "";
		
		if (periodo == null) {// esta vindo um periodo personalizado
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			String start = periodoInicial.format(formatter);
			String end = periodoFinal.format(formatter);
			
			query = "SELECT " + "metrics.impressions, " + "metrics.invalid_clicks, " + "metrics.clicks, "
					+ "metrics.cost_micros, " + "metrics.ctr, " + "metrics.cost_per_conversion " + "FROM campaign "
					+ "WHERE segments.date BETWEEN '"+start+"' AND '"+end+"'";

		} else {
			String filtroData = "";
			if(periodo.getName() != "ALL_TIME") {
				filtroData = "WHERE segments.date DURING "+periodo.getName();
			}
			
			query = "SELECT " + "metrics.impressions, " + "metrics.invalid_clicks, " + "metrics.clicks, "
					+ "metrics.cost_micros, " + "metrics.ctr, " + "metrics.cost_per_conversion " + "FROM campaign "
					+ filtroData;
			
			
		}

		return query;
	}

}
