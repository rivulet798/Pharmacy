package com.epam.service.utils.builder;

import java.util.LinkedList;
import java.util.List;

public class Builder {
    private static List dataForVerification = new LinkedList();

    public  static List getDataForVerification(){
        return dataForVerification;
    }

    public static Object[] setNullToDataForVerification() {
        dataForVerification.add("Test");
        dataForVerification.add(null);
        dataForVerification.add("Java");
        return dataForVerification.toArray();
    }

    public static Object[] setDataForVerificationWithoutNull() {
        dataForVerification.add("Test");
        dataForVerification.add("");
        dataForVerification.add(2018);
        return dataForVerification.toArray();
    }

    public static String[] setDataForVerificationWithEmptyString() {
        dataForVerification.add("");
        dataForVerification.add("Test");
        dataForVerification.add("Epam");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithoutEmptyString() {
        dataForVerification.add("2018");
        dataForVerification.add("Test function");
        dataForVerification.add("Epam");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDateForVerificationInWrongFormat() {
        dataForVerification.add("1997-02-12");
        dataForVerification.add("35-03-62");
        dataForVerification.add("Test");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDateForVerificationInRightFormat() {
        dataForVerification.add("1997-02-12");
        dataForVerification.add("2013-01-30");
        dataForVerification.add("2007-11-19");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithNotProperName() {
        dataForVerification.add("Masha");
        dataForVerification.add("Хоруженко");
        dataForVerification.add("test");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithWordInDifLan() {
        dataForVerification.add("Masha");
        dataForVerification.add("Хоruzhenko");
        dataForVerification.add("Test");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithProperName() {
        dataForVerification.add("Masha");
        dataForVerification.add("Хоруженко");
        dataForVerification.add("Test");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithWrongEmail() {
        dataForVerification.add("Masha@mail.ru");
        dataForVerification.add("@gmail.com");
        dataForVerification.add("Email@gmail.");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithRightEmail() {
        dataForVerification.add("masha@mail.ru");
        dataForVerification.add("email123@gmail.com");
        dataForVerification.add("email@tut.by");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithWrongLogin() {
        dataForVerification.add("15Login");
        dataForVerification.add("login15");
        dataForVerification.add("12345");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithRightLogin() {
        dataForVerification.add("Login15");
        dataForVerification.add("login");
        dataForVerification.add("myLogin");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithWrongPassword() {
        dataForVerification.add("password");
        dataForVerification.add("12345678");
        dataForVerification.add("pas123");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }

    public static String[] setDataForVerificationWithRightPassword() {
        dataForVerification.add("Password123");
        dataForVerification.add("passWord3");
        dataForVerification.add("123Passw");
        return (String[])dataForVerification.toArray(new String[dataForVerification.size()]);
    }
}
