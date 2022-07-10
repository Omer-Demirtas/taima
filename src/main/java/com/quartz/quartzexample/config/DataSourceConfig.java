package com.quartz.quartzexample.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
public class DataSourceConfig
{
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.target")
    public DataSourceProperties targetDataSourceProperties() {

        return new DataSourceProperties();

    }

    @Bean(name = "targetDataSource")
    @Primary
    @ConfigurationProperties("app.datasource.target.configuration")
    public DataSource targetDataSource() {
        return targetDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "targetEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean targetEntityManagerFactory(EntityManagerFactoryBuilder builder)
    {
        return builder
                .dataSource(targetDataSource())
                .properties(
                        new HashMap<String, String>() {{
                            //put("hibernate.hbm2ddl.auto", "none");
                            //put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
                            put("hibernate.transaction.flush_before_completion", "true");
                            put("hibernate.connection.release_mode", "after_transaction");
                            put("hibernate.connection.autocommit", "false");
                            put("hibernate.jdbc.batch_size", "50");
                            put("hibernate.order_inserts", "true");
                            put("hibernate.order_updates", "true");
                        }}
                )
                .build();
    }

    @Primary
    @Bean (name = "targetTransactionManager")
    public PlatformTransactionManager targetTransactionManager(final @Qualifier("targetEntityManagerFactory") LocalContainerEntityManagerFactoryBean targetEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(targetEntityManagerFactory.getObject()));
    }
}
