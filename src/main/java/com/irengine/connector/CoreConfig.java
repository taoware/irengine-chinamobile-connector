package com.irengine.connector;

import java.util.HashMap;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.irengine.connector.repository.core.CoreDatasourceProperties;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.irengine.connector.repository.core", entityManagerFactoryRef = "coreEntityManager", transactionManagerRef = "transactionManager")
@EnableConfigurationProperties(CoreDatasourceProperties.class)
public class CoreConfig {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	private CoreDatasourceProperties coreDatasourceProperties;

	@Bean(name = "coreDataSource", initMethod = "init", destroyMethod = "close")
	@Primary
	public DataSource coreDataSource() {
		JdbcDataSource h2XaDataSource = new JdbcDataSource();
		h2XaDataSource.setURL(coreDatasourceProperties.getUrl());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(h2XaDataSource);
		xaDataSource.setUniqueResourceName("xadscore");
		return xaDataSource;
	}

	@Bean(name = "coreEntityManager")
	@DependsOn("transactionManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean coreEntityManager() throws Throwable {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(coreDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan("com.irengine.connector.domain.core");
		entityManager.setPersistenceUnitName("corePersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}
