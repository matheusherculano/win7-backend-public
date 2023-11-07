package com.gestor.app.dbConfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gestor.app.oldPhpApp.repository.TblClienteRepository;
import com.gestor.app.oldPhpApp.repository.TblLeadRepository;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {TblClienteRepository.class, TblLeadRepository.class }, entityManagerFactoryRef = "phpEntityManager")
public class PhpDbConfig {

	@Bean
	@ConfigurationProperties(prefix = "php.datasource")
	public DataSource phpDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean phpEntityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("phpDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.gestor.app.oldPhpApp.model").build();
	}

}
