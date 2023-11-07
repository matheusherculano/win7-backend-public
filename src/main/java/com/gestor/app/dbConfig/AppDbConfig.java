package com.gestor.app.dbConfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gestor.app.repository.AmbienteRepository;
import com.gestor.app.repository.UsuarioRepository;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { AmbienteRepository.class,
		UsuarioRepository.class }, entityManagerFactoryRef = "appEntityManager")
public class AppDbConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "app.datasource")
	public DataSource appDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean appEntityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("appDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.gestor.app.model").build();
	}

}