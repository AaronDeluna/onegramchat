package com.javaacademy.onegramchat.exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super("Ошибка: " + message);
    }
}
