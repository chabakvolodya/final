package com.sk.converter;

import com.sk.model.DishCategory;
import org.springframework.core.convert.converter.Converter;

public class StringToDishCategoryConverter implements Converter<String, DishCategory> {
    @Override
    public DishCategory convert(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        DishCategory category = new DishCategory();

        String[] dishCategory = input.split(",");
        if (dishCategory.length != 2) {
            return null;
        } else {
            category.setId(Integer.parseInt(dishCategory[0]));
            category.setDescription(dishCategory[1]);
        }

        return category;
    }
}
