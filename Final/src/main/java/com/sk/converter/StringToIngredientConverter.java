package com.sk.converter;

import com.sk.model.Ingredient;
import org.springframework.core.convert.converter.Converter;

public class StringToIngredientConverter implements Converter<String, Ingredient> {


    @Override
    public Ingredient convert(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(Integer.parseInt(input));

        return ingredient;
    }
}
