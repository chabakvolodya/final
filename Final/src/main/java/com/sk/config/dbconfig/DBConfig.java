package com.sk.config.dbconfig;

import javax.sql.DataSource;

public interface DBConfig {

    DataSource createDataSource();

}
