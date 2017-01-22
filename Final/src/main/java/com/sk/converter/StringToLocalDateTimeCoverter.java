package com.sk.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class StringToLocalDateTimeCoverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(input, dtf).atStartOfDay();
    }

}
