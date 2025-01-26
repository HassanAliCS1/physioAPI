package com.fyp.physioAPI.user.service;

import com.fyp.physioAPI.user.userExceptions.AuthException;

import java.util.regex.Pattern;

public class NewUserValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final int MIN_PASSWORD_LENGTH = 8;

    public static void validateEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new AuthException("Invalid email format");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new AuthException("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
        }
    }

    public static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new AuthException(fieldName + " cannot be empty");
        }
    }
}
