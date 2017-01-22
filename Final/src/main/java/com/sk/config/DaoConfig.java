package com.sk.config;

import com.sk.dao.*;
import com.sk.dao.impl.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class DaoConfig {

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Properties hibernateProperties;

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(dataSource);
        lsfb.setPackagesToScan("com.sk.model");
        lsfb.setHibernateProperties(hibernateProperties);
        try {
            lsfb.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lsfb.getObject();
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        SQLErrorCodeSQLExceptionTranslator errorTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
        jdbcTemplate.setExceptionTranslator(errorTranslator);

        return jdbcTemplate;
    }

    @Bean(name = "txManager")
    public HibernateTransactionManager txManager() {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(sessionFactory());
        return htm;
    }

    @Bean(name = "employeeDao")
    public EmployeeDao employeeDao() {
        return new HEmployeeDao();
    }

    @Bean(name = "positionDao")
    public PositionDao positionDao() {
        return new HPositionDao();
    }

    @Bean(name = "dishDao")
    public DishDao dishDao() {
        return new HDishDao();
    }

    @Bean(name = "dishCategoryDao")
    public DishCategoryDao dishCategoryDao() {
        return new HDishCategoryDao();
    }

    @Bean(name = "boardDao")
    public BoardDao boardDao() {
        return new HBoardDao();
    }

    @Bean(name = "ingredientDao")
    public IngredientDao ingredientDao() {
        return new HIngredientDao();
    }

    @Bean(name = "orderDao")
    public OrderDao orderDao() {
        return new HOrderDao();
    }

    @Bean(name = "menuDao")
    public MenuDao menuDao() {
        return new HMenuDao();
    }

    @Bean(name = "orderStatusDao")
    public OrderStatusDao orderStatusDao() {
        return new HOrderStatusDao();
    }

}
