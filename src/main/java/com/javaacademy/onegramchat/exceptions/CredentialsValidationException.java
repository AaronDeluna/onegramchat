package com.javaacademy.onegramchat.exceptions;

public class CredentialsValidationException extends Exception {

    public CredentialsValidationException(String message) {
        super("Ошибка: " + message);
    }
}
