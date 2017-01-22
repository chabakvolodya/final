package com.sk.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext context = super.createRootApplicationContext();
        ((ConfigurableEnvironment) context.getEnvironment()).setActiveProfiles(getProfile());
        return context;
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebAppConfig.class, AppConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    private String getProfile() {
        Properties prop = new Properties();
        String result = "";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("profile.properties")) {
            prop.load(input);
            result = prop.getProperty("active.profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

