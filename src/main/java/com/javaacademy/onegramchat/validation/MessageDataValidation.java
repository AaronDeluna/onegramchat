package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.MessageInputException;
import com.javaacademy.onegramchat.exceptions.UserNotFoundException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;

import java.util.Map;

public class MessageDataValidation {

    public static void massageInputValidation(String recipientName, String messageText) throws MessageInputException {
        if (recipientName.trim().isEmpty()) {
            throw new MessageInputException("Имя получателя не может быть пустым, попробуйте еще раз!");
        }
        if (messageText.trim().isEmpty()) {
            throw new MessageInputException("Текст сообщения не может быть пустым, попробуйте еще раз!");
        }
    }

    public static void userExistValidation(String name, Map<String, User> users)
            throws UserNotFoundException {
        if (users.containsKey(name)) {
            throw new UserNotFoundException("Пользователь: " + name + " не найден");
        }
    }
}
