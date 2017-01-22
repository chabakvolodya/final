package com.sk.config.dbconfig;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Profile("prod")
@Configuration
public class ProdDBConfig implements DBConfig {

    @Autowired
    private Environment env;

    @Override
    @Bean(name = "dataSource")
    public DataSource createDataSource() {
        System.out.println("In PROD");
        ComboPooledDataSource c3poDataSource = new ComboPooledDataSource();

        try {
            c3poDataSource.setDriverClass(env.getProperty("jdbc.postgres.driver.class"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        c3poDataSource.setJdbcUrl(env.getProperty("jdbc.postgres.url"));
        c3poDataSource.setUser(env.getProperty("jdbc.postgres.user"));
        c3poDataSource.setPassword(env.getProperty("jdbc.postgres.password"));
        c3poDataSource.setMinPoolSize(env.getProperty("jdbc.c3po.min.connections", Integer.class));
        c3poDataSource.setMaxPoolSize(env.getProperty("jdbc.c3po.max.connections", Integer.class));
        c3poDataSource.setAcquireIncrement(env.getProperty("jdbc.c3po.acquire.increment", Integer.class));

        return c3poDataSource;
    }

    @Bean(name = "hibernateProperties")
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.max_fetch_depth", "3");
        properties.put("hibernate.jdbc.fetch_size", "50");
        properties.put("hibernate.jdbc.batch_size", "10");
        return properties;
    }
}
