package com.sk.constraint;

import org.joda.money.Money;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinJodaMoneyValidator implements ConstraintValidator<MinJodaMoney, Money> {

    private long value;

    @Override
    public void initialize(MinJodaMoney validMoney) {
        value = validMoney.value();
    }

    @Override
    public boolean isValid(Money object, ConstraintValidatorContext constraintContext) {
        return object == null || object.getAmount().doubleValue() >= value;
    }
}
