package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.exceptions.MessageInputException;

public class MessageValidation {

    /**
     * Проверяет корректность ввода данных для сообщения.
     *
     * @param recipientName имя получателя сообщения.
     * @param messageText   текст сообщения.
     * @throws MessageInputException если имя получателя или текст сообщения пустые.
     */
    public static void massageInputValidation(String recipientName, String messageText) throws MessageInputException {
        if (recipientName.trim().isEmpty()) {
            throw new MessageInputException("Имя получателя не может быть пустым, попробуйте еще раз!");
        }
        if (messageText.trim().isEmpty()) {
            throw new MessageInputException("Текст сообщения не может быть пустым, попробуйте еще раз!");
        }
    }
}
