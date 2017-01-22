package com.sk.config;

import com.sk.converter.*;
import com.sk.service.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestControllerConfig {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public DishService dishService() {
        return Mockito.mock(DishService.class);
    }

    @Bean
    public EmployeeService employeeService() {
        return Mockito.mock(EmployeeService.class);
    }

    @Bean
    public MenuService menuService() {
        return Mockito.mock(MenuService.class);
    }

    @Bean
    public DishCategoryService dishCategoryService() {
        return Mockito.mock(DishCategoryService.class);
    }

    @Bean
    public IngredientService ingredientService() {
        return Mockito.mock(IngredientService.class);
    }

    @Bean(name = "conversionService")
    public FormattingConversionService conversionService() {
        FormattingConversionService conversionService = new FormattingConversionService();

        conversionService.addConverter(new StringToMoneyConverter());
        conversionService.addConverter(new StringToDishConverter());
        conversionService.addConverter(new StringToIngredientConverter());
        conversionService.addConverter(new DishToStringConverter());
        conversionService.addConverter(new StringToDishCategoryConverter());
        conversionService.addConverter(new StringToLocalDateConverter());
        conversionService.addConverter(new StringToPositionConverter());
        conversionService.addConverter(new StringToLocalDateTimeCoverter());

        return conversionService;
    }

}
