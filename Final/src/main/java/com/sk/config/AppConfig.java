package com.sk.config;

import com.sk.config.dbconfig.DevDBConfig;
import com.sk.config.dbconfig.ProdDBConfig;
import com.sk.service.*;
import com.sk.service.impl.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = {
        "classpath:jdbc.properties",
        "classpath:liquibase.properties",
        "classpath:profile.properties",
})
@EnableTransactionManagement
@ComponentScan({"com.sk.model",
        "com.sk.dao",
        "com.sk.service",
        "com.sk.converter",})
@Import({DaoConfig.class, DevDBConfig.class, ProdDBConfig.class})
public class AppConfig {

    @Bean(name = "employeeService")
    public EmployeeService employeeService() {
        return new EmployeeServiceImpl();
    }

    @Bean
    public DishService dishService() {
        return new DishServiceImpl();
    }

    @Bean
    public MenuService menuService() {
        return new MenuServiceImpl();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

    @Bean
    public DishCategoryService dishCategoryService() {
        return new DishCaregoryServiceImpl();
    }

    @Bean(name = "ingredientService")
    public IngredientService ingredientService() {
        return new IngredientServiceImpl();
    }

    @Bean(name = "positionService")
    public PositionService positionService() {
        return new PositionServiceImpl();
    }

}
