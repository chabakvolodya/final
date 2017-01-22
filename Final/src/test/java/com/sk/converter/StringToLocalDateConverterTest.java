package com.sk.converter;

import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class StringToLocalDateConverterTest {
    @Test
    public void convert() throws Exception {
        Converter<String, LocalDate> converter = new StringToLocalDateConverter();

        int year = 1990;
        int month = 1;
        int dayOfMonth = 25;

        String input = "25/01/1990";

        LocalDate expectedDate = LocalDate.of(year, month, dayOfMonth);

        assertEquals(expectedDate, converter.convert(input));
    }

}