package com.javaacademy.onegramchat.exceptions;

public class UserRegistrationException extends Exception {

    public UserRegistrationException(String message) {
        super("Ошибка: " + message);
    }
}
