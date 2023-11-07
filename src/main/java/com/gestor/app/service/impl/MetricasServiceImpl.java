package com.gestor.app.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestor.app.DTO.AdsClienteDTO;
import com.gestor.app.DTO.DateRangeDTO;
import com.gestor.app.DTO.MetricasDTO;
import com.gestor.app.DTO.RequestMetricsDTO;
import com.gestor.app.enums.PeriodoAds;
import com.gestor.app.google.service.AdsCampaingService;
import com.gestor.app.oldPhpApp.model.TblCliente;
import com.gestor.app.oldPhpApp.repository.MetricasRepositoryCustom;
import com.gestor.app.oldPhpApp.repository.TblClienteRepository;
import com.gestor.app.service.MetricasService;
import com.gestor.app.service.Phone3cxService;
import com.gestor.app.util.FiltroDatasSQL;

@Service
public class MetricasServiceImpl implements MetricasService {

	@Autowired
	private AdsCampaingService adsCampaingService;

	@Autowired
	private MetricasRepositoryCustom metricasRepositoryCustom;

	@Autowired
	private Phone3cxService phone3cxService;

	@Autowired
	private TblClienteRepository tblClienteRepository;

	@Override
	public MetricasDTO obterMetricasByCliente(RequestMetricsDTO requestDTO) throws IOException {
		TblCliente tblCliente = tblClienteRepository.findByIdTblCliente(requestDTO.getClienteId());
		
		AdsClienteDTO adsDTO = null;
		Long metricasForms = 0L;
		Long metricasWpp = 0L;
		Long metricasLigacoes = 0L;
		Long qtdDiasFiltrados = 0L;

		if (requestDTO.getPeriodoAds() == null) {
			adsDTO = adsCampaingService.getMetricasAllCampaingsPeriodoPersonalizado(requestDTO.getAdwords(),
					requestDTO.getPeriodoInicial(), requestDTO.getPeriodoFinal());

			metricasLigacoes = phone3cxService.obterQtdLigacoesByCliente(requestDTO.getPeriodoInicial(),
					requestDTO.getPeriodoFinal(), tblCliente.getRamal());

			/* metricas que estão no db da win7 php - //ex ('Formulário', 'WhatsApp') */
			metricasForms = metricasRepositoryCustom.obterMetricas(requestDTO.getClienteId(), "Formulário",
					FiltroDatasSQL.filterDateWithBetween("PERSONALIZADO", requestDTO.getPeriodoInicial(),
							requestDTO.getPeriodoFinal()));
			metricasWpp = metricasRepositoryCustom.obterMetricas(requestDTO.getClienteId(), "WhatsApp", FiltroDatasSQL
					.filterDateWithBetween("PERSONALIZADO", requestDTO.getPeriodoInicial(), requestDTO.getPeriodoFinal()));
			
			qtdDiasFiltrados = FiltroDatasSQL.calcularQuantidadeDias(requestDTO.getPeriodoInicial(), requestDTO.getPeriodoFinal());
		} else {// periodos predefinidos utilizados no padrão da api Google ADS --- caso != null
			adsDTO = adsCampaingService.getMetricasAllCampaingsPeriodoAds(requestDTO.getAdwords(),
					requestDTO.getPeriodoAds());

			/* metricas que estão no db da win7 php - //ex ('Formulário', 'WhatsApp') */
			metricasForms = metricasRepositoryCustom.obterMetricas(requestDTO.getClienteId(), "Formulário",
					FiltroDatasSQL.filterDateWithBetween(requestDTO.getPeriodoAds().getName(), null, null));
			metricasWpp = metricasRepositoryCustom.obterMetricas(requestDTO.getClienteId(), "WhatsApp",
					FiltroDatasSQL.filterDateWithBetween(requestDTO.getPeriodoAds().getName(), null, null));
			
			metricasLigacoes = obterMetricasLigacoesPeriodoAds(requestDTO.getPeriodoAds(), tblCliente.getRamal());
			
			qtdDiasFiltrados = FiltroDatasSQL.filterDateWithStartEnd(requestDTO.getPeriodoAds().getName()).getQtdDias();
		}

		MetricasDTO metricasDTO = new MetricasDTO(metricasForms, metricasWpp, metricasLigacoes, adsDTO, qtdDiasFiltrados);
		return metricasDTO;
	}
	
	private Long obterMetricasLigacoesPeriodoAds(PeriodoAds periodoAds, String ramalCliente) {
		Long metricasLigacoes = 0L;
		
		DateRangeDTO datas = FiltroDatasSQL.filterDateWithStartEnd(periodoAds.getName());
		
		metricasLigacoes = phone3cxService.obterQtdLigacoesByCliente(datas.getDataInicial(),
				datas.getDataFinal(), ramalCliente);
		
		return metricasLigacoes;
	}

}
