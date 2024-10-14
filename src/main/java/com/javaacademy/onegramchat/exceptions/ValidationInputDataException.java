package com.javaacademy.onegramchat.exceptions;

public class ValidationInputDataException extends Exception {

    public ValidationInputDataException(String message) {
        super("Ошибка: " + message);
    }
}
