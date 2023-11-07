package com.gestor.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig implements CorsConfigurationSource{

	@Autowired
	private FilterToken filterToken;

	@Autowired
	private CorsConfigurationSource corsConfigurationSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
		.cors().configurationSource((request -> new CorsConfiguration().applyPermitDefaultValues()));
		
//		 http.cors().configurationSource(corsConfigurationSource);

		return http.authorizeHttpRequests(requests -> {
			requests.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
			requests.requestMatchers(HttpMethod.GET, "/cliente/metrics/*").permitAll();
			requests.requestMatchers(HttpMethod.GET, "/testes/1").permitAll();
			requests.requestMatchers(HttpMethod.POST, "/testes/2").permitAll();
			requests.requestMatchers("/logout").permitAll();
			requests.anyRequest().authenticated().and().addFilterBefore(filterToken,
					UsernamePasswordAuthenticationFilter.class);
		}).build();
	}

	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
      CorsConfiguration config = new CorsConfiguration();
      config.addAllowedOrigin("*");
      config.addAllowedMethod("*");
      config.addAllowedHeader("*");
//      config.setAllowCredentials(true);
      return config;
	}

}
