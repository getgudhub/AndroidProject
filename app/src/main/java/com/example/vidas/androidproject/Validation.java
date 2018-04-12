package com.example.vidas.androidproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String VALID_CREDENTIALS_REGEX ="^[A-Za-z0-9]{5,13}$";
    private static final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$";
    private static final String VALID_ID_REGEX = "\\d{1,11}$";
    private static final String VALID_TIME_REGEX = "[0-9]{1,4}$";
    private static final String VALID_DATE_REGEX = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
    private static final String VALID_NAME_REGEX = "[A-Za-z0-9#/ ]{4,20}$";
    private static final String VALID_FULL_NAME_REGEX= "[-A-za-z ]{3,20}$";

    public static boolean isValidCredentials(String credentials){
        Pattern credentialsPattern = Pattern.compile(VALID_CREDENTIALS_REGEX);
        Matcher credentialsMatcher = credentialsPattern.matcher(credentials);
        return credentialsMatcher.find();
    }

    public static boolean isValidName(String name){
        Pattern pattern = Pattern.compile(VALID_NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public static boolean isValidEmail(String email){
        Pattern emailPattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.find();
    }

    public static boolean isValidTime(String size){
        Pattern emailPattern = Pattern.compile(VALID_TIME_REGEX);
        Matcher emailMatcher = emailPattern.matcher(size);
        return emailMatcher.find();
    }

    public static boolean isValidDate(String date){
        Pattern credentialsPattern = Pattern.compile(VALID_DATE_REGEX);
        Matcher credentialsMatcher = credentialsPattern.matcher(date);
        return credentialsMatcher.find();
    }

    public static boolean isValidFullName(String credentials){
        Pattern credentialsPattern = Pattern.compile(VALID_FULL_NAME_REGEX);
        Matcher credentialsMatcher = credentialsPattern.matcher(credentials);
        return credentialsMatcher.find();
    }

    /* //Bonus
    public static boolean isValidId(String id){
        Pattern emailPattern = Pattern.compile(VALID_ID_REGEX);
        Matcher emailMatcher = emailPattern.matcher(id);
        if(id.contains(".")){
            return false;
        }else {
            return emailMatcher.find();
        }
    }*/
}