package com.sk.converter;

import com.sk.model.Dish;
import org.springframework.core.convert.converter.Converter;

public class DishToStringConverter implements Converter<Dish, String> {
    @Override
    public String convert(Dish input) {
        if (input == null ) {
            return null;
        }

        return String.valueOf(input.getId());
    }
}
