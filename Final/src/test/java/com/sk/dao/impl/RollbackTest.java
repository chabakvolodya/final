package com.sk.dao.impl;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration
public class RollbackTest extends BaseDatabaseTest {

    @Value("${liquibase.changeLog}")
    private String liquibaseChangeLog;

    @Test
    public void shouldUpdateAndRollbackSuccessfully() throws Exception {
        Liquibase liquibase = new Liquibase(
                liquibaseChangeLog,
                new ClassLoaderResourceAccessor(),
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()))
        );

        liquibase.update(NO_CONTEXTS);
        liquibase.rollback(1, NO_CONTEXTS);

    }


}
