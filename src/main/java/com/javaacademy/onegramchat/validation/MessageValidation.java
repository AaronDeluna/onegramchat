package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.MessageInputException;
import com.javaacademy.onegramchat.exceptions.NoMessagesException;

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

    /**
     * Проверяет наличие сообщений у пользователя.
     *
     * @param user пользователь, чьи сообщения проверяются
     * @throws NoMessagesException если у пользователя нет доступных сообщений
     */
    public static void verifyUserMessages(User user) throws NoMessagesException {
        if (user.getMessages().isEmpty()) {
            throw new NoMessagesException("У вас нет доступных сообщений.");
        }
    }
}
