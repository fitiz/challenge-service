package com.fitiz.challenge.configuration;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

  @Value("${spring.datasource.url}")
  String jdbcUrl;

  @Value("${spring.datasource.username}")
  String username;

  @Value("${spring.datasource.password}")
  String password;

  @Value("${spring.datasource.driver-class-name}")
  String driverClassName;

  @Bean(name = "dataSource")
  DataSource getDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName(driverClassName);
    return dataSource;
  }

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
          @Qualifier("dataSource") DataSource dataSource) {

    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.fitiz.challenge.model");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return em;
  }

  @Bean(name="transactionManager")
  DataSourceTransactionManager getDataSourceTransactionManager() {
    return new DataSourceTransactionManager(getDataSource());
  }

  @Bean(name="transactionAwareDataSource")
  TransactionAwareDataSourceProxy getTransactionAwareDataSourceProxy() {
    return new TransactionAwareDataSourceProxy(getDataSource());
  }

  @Bean(name="connectionProvider")
  DataSourceConnectionProvider getDataSourceConnectionProvider() {
    return new DataSourceConnectionProvider(getTransactionAwareDataSourceProxy());
  }

  @Bean
  DefaultDSLContext getDefaultDSLContext() {
    return new DefaultDSLContext(getConfiguration());
  }

  @Bean
  DefaultConfiguration getConfiguration() {
    DefaultConfiguration config = new DefaultConfiguration();
    config.set(SQLDialect.POSTGRES);
    config.setConnectionProvider(getDataSourceConnectionProvider());
    return config;
  }
}
