package com.sk.dao.impl;

import com.sk.config.AppConfig;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.logging.LogLevel;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Вот здесь используй либо @EnableAutoConfiguration
 * либо явно указывай какие конфигурации поднимать:
 * DataSourceAutoConfiguration.class,
 * LiquibaseAutoConfiguration.class,
 * DatabaseMigrationTest.class
 * <p>
 * В этом тесте я оставила явные конфигурации для наглядности.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
public abstract class BaseDatabaseTest extends AbstractTransactionalJUnit4SpringContextTests{

    private static final String H2DropAllSql = "DROP ALL OBJECTS";
    protected static final String NO_CONTEXTS = "";

    @Autowired
    protected DataSource dataSource;

    @Value("${liquibase.changeLog}")
    private String liquibaseChangeLog;

    @Before
    public void setUp() throws Exception {
        Liquibase liquibase = new Liquibase(
                liquibaseChangeLog,
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(dataSource.getConnection()));

        liquibase.getLog().setLogLevel(LogLevel.WARNING);

        liquibase.update(NO_CONTEXTS);
    }

    @After
    public void tearDown() throws SQLException {
        dataSource.getConnection().createStatement().execute(H2DropAllSql);
    }

}
