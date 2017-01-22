package com.sk.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/MM/yyyy");

        return LocalDate.parse(input, dtf);
    }

}
