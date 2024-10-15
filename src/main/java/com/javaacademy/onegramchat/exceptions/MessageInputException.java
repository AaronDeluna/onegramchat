package com.javaacademy.onegramchat.exceptions;

public class MessageInputException extends Exception {

    public MessageInputException(String message) {
        super("Ошибка: " + message);
    }
}
