package com.sk.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;


public class StringToMoneyConverter implements Converter<String, Money> {
    @Override
    public Money convert(String input) {
        if (input == null) {
            return null;
        } else if (input.isEmpty()) {
            return Money.of(CurrencyUnit.USD, 0D);
        }

        input = input.replace("USD", "").trim();

        double amount;
        try {
            amount = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Wrong price.");
        }
        Money money = Money.of(CurrencyUnit.USD, amount);
        return money;
    }

}
