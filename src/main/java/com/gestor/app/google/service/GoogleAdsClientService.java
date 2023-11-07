package com.gestor.app.google.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.auth.Credentials;
import com.google.auth.oauth2.UserCredentials;

@Service
public class GoogleAdsClientService {
	
	@Autowired
    private Environment environment;
	

	/** Setando as credenciais para acessar a API GOOGLE ADS**/
	public GoogleAdsClient get() throws IOException {

		Credentials credentials = UserCredentials.newBuilder().setClientId(environment.getProperty("ads.clientId"))
				.setClientSecret(environment.getProperty("ads.clientSecret"))
				.setRefreshToken(environment.getProperty("ads.refreshToken")).build();

		GoogleAdsClient googleAdsClient = GoogleAdsClient.newBuilder()
				.setDeveloperToken(environment.getProperty("ads.developerToken")).setLoginCustomerId(Long.parseLong(environment.getProperty("ads.loginCustomerId")))
				.setCredentials(credentials).build();

		return googleAdsClient;
	}

}
