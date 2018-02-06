package com.epam.service.utils;

import com.epam.service.utils.builder.Builder;
import com.epam.exception.ValidatorException;
import org.junit.After;
import org.junit.Test;


public class ValidatorTest {

    @After
    public void clearDataForVerification() {
        Builder.getDataForVerification().clear();
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenInputContainsNullValue() throws ValidatorException{
        Object[] inputData = Builder.setNullToDataForVerification();
        Validator.isNull(inputData);
    }

    @Test
    public void shouldNotThrowExceptionWhenInputIsNotContainsNullValue() throws ValidatorException{
        Object[] inputData = Builder.setDataForVerificationWithoutNull();
        Validator.isNull(inputData);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenInputContainsEmptyString() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithEmptyString();
        Validator.isEmptyString(inputData);
    }

    @Test
    public void shouldNotThrowExceptionWhenInputIsNotContainsEmptyString() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithoutEmptyString();
        Validator.isEmptyString(inputData);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenEnterDateInWrongFormate() throws ValidatorException{
        String[] inputData = Builder.setDateForVerificationInWrongFormat();
        Validator.matchDate(inputData);
    }

    @Test
    public void shouldNotThrowExceptionWhenEnterDateInRightFormat() throws ValidatorException{
        String[] inputData = Builder.setDateForVerificationInRightFormat();
        Validator.matchDate(inputData);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenInputIsNotProperName() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithNotProperName();
        Validator.matchProperName(inputData);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenInputWordInDifferentLanguages() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithWordInDifLan();
        Validator.matchProperName(inputData);
    }

    @Test
    public void shouldNotThrowExceptionWhenInputIsProperName() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithProperName();
        Validator.matchProperName(inputData);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenWrongEmailFormat() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithWrongEmail();
        Validator.matchEmail(inputData);
    }

    @Test
    public void shouldNotThrowExceptionWhenRightEmailFormat() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithRightEmail();
        Validator.matchEmail(inputData);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenWrongLoginFormat() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithWrongLogin();
        Validator.matchLogin(inputData);
    }

    @Test
    public void shouldNotThrowExceptionWhenRightLoginFormat() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithRightLogin();
        Validator.matchLogin(inputData);
    }

    @Test(expected = ValidatorException.class)
    public void shouldThrowExceptionWhenWrongPasswordFormat() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithWrongPassword();
        Validator.matchPassword(inputData);
    }

    @Test
    public void shouldNotThrowExceptionWhenRightPasswordFormat() throws ValidatorException{
        String[] inputData = Builder.setDataForVerificationWithRightPassword();
        Validator.matchPassword(inputData);
    }
}