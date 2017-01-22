package com.sk.converter;

import com.sk.model.DishCategory;
import com.sk.model.Position;
import org.springframework.core.convert.converter.Converter;

public class StringToPositionConverter implements Converter<String, Position> {
    @Override
    public Position convert(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        Position position = new Position();

        String[] posData = input.split(",");
        if (posData.length != 2) {
            return null;
        } else {
            position.setId(Integer.parseInt(posData[0]));
            position.setDescription(posData[1]);
        }

        return position;
    }
}
