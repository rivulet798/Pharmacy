package com.epam.service.utils;

import com.epam.service.utils.exception.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String REGEX_FOR_PROPER_NAME = "([A-Z]{1}[a-z]+)|([А-Я]{1}[а-я]+)";
    private static final String REGEX_FOR_LOGIN = "^[a-zA-Z](.[a-zA-Z0-9_-]*)$";
    private static final String REGEX_FOR_PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
    private static final String REGEX_FOR_EMAIL = "[0-9a-z_-]+@[0-9a-z_-]+\\.[a-z]{2,5}";
    private static final String REGEX_FOR_DATE = "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)";
    private static final String REGEX_FOR_NUMBER = "\\d+";

    private static Pattern pattern;
    private static Matcher matcher;

    public final static void isNull(Object... objects) throws ValidatorException {
        for (Object ob : objects) {
            if (ob == null) {
                throw new ValidatorException("Input error (string is null)");
            }
        }
    }

    public final static void isEmptyString(String... strings) throws ValidatorException {
        for (String s : strings) {
            if (s.isEmpty()) {
                throw new ValidatorException("Input error (string is empty)");
            }
        }
    }


    public final static void matchDate(String... strings) throws ValidatorException {
        pattern = Pattern.compile(REGEX_FOR_DATE);
        try {
            for (String s : strings) {
                matcher = pattern.matcher(s);
                if (!matcher.matches()) {
                    throw new ValidatorException("Date format error");
                }
            }
        } catch (NullPointerException e) {
            throw new ValidatorException(e);
        }
    }

    public final static void matchProperName(String... strings) throws ValidatorException {
        pattern = Pattern.compile(REGEX_FOR_PROPER_NAME);
        for (String s : strings) {
            matcher = pattern.matcher(s);
            if (!matcher.matches()) {
                throw new ValidatorException("Name format error");
            }
        }
    }

    public final static void matchEmail(String... strings) throws ValidatorException {
        pattern = Pattern.compile(REGEX_FOR_EMAIL);
        for (String s : strings) {
            matcher = pattern.matcher(s);
            if (!matcher.matches()) {
                throw new ValidatorException("E-mail format error");
            }
        }
    }

    public final static void matchLogin(String... strings) throws ValidatorException {
        pattern = Pattern.compile(REGEX_FOR_LOGIN);
        for (String s : strings) {
            matcher = pattern.matcher(s);
            if (!matcher.matches()) {
                throw new ValidatorException("Login format error");
            }
        }
    }

    public final static void matchPassword(String... strings) throws ValidatorException {
        pattern = Pattern.compile(REGEX_FOR_PASSWORD);
        for (String s : strings) {
            matcher = pattern.matcher(s);
            if (!matcher.matches()) {
                throw new ValidatorException("Password format error");
            }
        }
    }

    public final static void matchNumber(String... strings) throws ValidatorException{
        pattern = Pattern.compile(REGEX_FOR_NUMBER);
        for (String s : strings) {
            matcher = pattern.matcher(s);
            if (!matcher.matches()|| Integer.parseInt(s) < 0) {
                throw new ValidatorException("Number format error");
            }
        }

    }
}
