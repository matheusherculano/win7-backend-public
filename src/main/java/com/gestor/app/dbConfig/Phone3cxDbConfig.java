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

import com.gestor.app.phone3cx.repository.Phone3cxCustomRepository;
import com.gestor.app.phone3cx.repository.Phone3cxRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {Phone3cxRepository.class, Phone3cxCustomRepository.class },entityManagerFactoryRef = "phone3cxEntityManager")
public class Phone3cxDbConfig {

	@Bean
	@ConfigurationProperties(prefix = "phone3cx.datasource")
	public DataSource phone3cxDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean phone3cxEntityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("phone3cxDataSource") DataSource dataSource) {
		return 
				builder.dataSource(dataSource)
				.packages("com.gestor.app.phone3xc.model") //caso de mapear classes
				.build();
	}
}
