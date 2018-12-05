package com.contactBook.contactBook.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "maindbFactory", transactionManagerRef = "mainDntrmanager", basePackages = {
		"com.contactBook.contactBook.repository" })
public class dbCofig {
	private String modelPackege = "com.contactBook.contactBook.model";

	@Bean(name = "mainDntrmanager")
	public PlatformTransactionManager mainTransactionManager(
			@Qualifier("maindbFactory") LocalContainerEntityManagerFactoryBean deliveryEntityManagerFactory) {
		return new JpaTransactionManager(deliveryEntityManagerFactory.getObject());
	}

	@Bean(name = "vendorAdaptor")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		// hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "maindbFactory")
	public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
			@Qualifier("vendorAdaptor") JpaVendorAdapter jpaVendorAdapter,
			@Qualifier("jpaProperties") Properties jpaProperties,
			@Qualifier("mainDbdatasourse") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setJpaProperties(jpaProperties);
		factoryBean.setPersistenceUnitName("maindb");
		factoryBean.setPackagesToScan(modelPackege.split(","));
		return factoryBean;
	}

	@Bean(name = "jpaProperties")
	public Properties jpaProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.ejb.naming_strategy",
				"org.hibernate.cfg.ImprovedNamingStrategy");
		props.setProperty("hibernate.jdbc.time_zone", "UTC");
		props.setProperty("hibernate.jdbc.show-sql", "false");
		props.setProperty("show_sql", "false");
		return props;
	}

	@Bean(name = "mainDbdatasourse")
	@ConfigurationProperties(prefix = "datasource")
	public DataSource mainDbdatasourse() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mainjdbcTemplate")
	public JdbcTemplate mainJdbcTemplate(
			@Qualifier("mainDbdatasourse") DataSource mMySql) {
		return new JdbcTemplate(mMySql);
	}
}
