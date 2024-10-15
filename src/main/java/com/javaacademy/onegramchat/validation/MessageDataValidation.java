package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.MessageInputException;
import com.javaacademy.onegramchat.exceptions.UserNotFoundException;

import java.util.Map;

public class MessageDataValidation {

    /**
     * Проверяет корректность ввода данных для сообщения.
     *
     * @param recipientName имя получателя сообщения.
     * @param messageText текст сообщения.
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
     * Проверяет, существует ли пользователь с заданным именем в системе.
     *
     * @param name имя пользователя, которого необходимо проверить.
     * @param users карта, содержащая всех пользователей, где ключом является имя пользователя.
     * @throws UserNotFoundException если пользователь с указанным именем не найден.
     */
    public static void userExistValidation(String name, Map<String, User> users)
            throws UserNotFoundException {
        if (!users.containsKey(name)) {
            throw new UserNotFoundException("Пользователь: " + name + " не найден");
        }
    }
}
