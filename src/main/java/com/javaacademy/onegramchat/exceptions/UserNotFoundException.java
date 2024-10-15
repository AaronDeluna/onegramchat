package com.javaacademy.onegramchat.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super("Ошибка: " + message);
    }
}
