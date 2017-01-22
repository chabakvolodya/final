package com.sk.converter;

import com.sk.model.Dish;
import org.springframework.core.convert.converter.Converter;

public class StringToDishConverter implements Converter<String, Dish> {
    @Override
    public Dish convert(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        Dish dish = new Dish();
        dish.setId(Integer.parseInt(input));

        return dish;
    }
}
