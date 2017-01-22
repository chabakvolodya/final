package com.sk.config.dbconfig;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.logging.LogLevel;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Profile("dev")
@Configuration
public class DevDBConfig implements DBConfig {

    private static final Logger log = LoggerFactory.getLogger(DevDBConfig.class);

    @Autowired
    private Environment env;


    @Override
    @Bean(name = "dataSource")
    public DataSource createDataSource() {
        log.debug("Profile - 'DEV'");
        return dataSourceH2();
    }

    @Bean(name = "hibernateProperties")
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.max_fetch_depth", "3");
        properties.put("hibernate.jdbc.fetch_size", "50");
        properties.put("hibernate.jdbc.batch_size", "10");
        return properties;
    }

    private DataSource dataSourceH2() {
        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase dataSource = builder
                .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
                .setName("rest")
                .build();

        liquibaseUpdate(dataSource);

        return dataSource;
    }

    private void liquibaseUpdate(DataSource dataSource) {
        try {
            Liquibase liquibase;
            liquibase = new Liquibase(
                    env.getProperty("liquibase.changeLog"),
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(dataSource.getConnection()));

            liquibase.getLog().setLogLevel(LogLevel.INFO);

            liquibase.update("");

        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException("DB has not created: " + e);
        }

    }

}
