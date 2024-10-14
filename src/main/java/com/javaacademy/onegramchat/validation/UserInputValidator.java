package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.exceptions.ValidationInputDataException;

public class UserInputValidator {

    public static final String EMPTY_NAME_MESSAGE = "Имя не может быть пустым, попробуйте еще раз.";
    public static final String EMPTY_PASSWORD_MESSAGE = "Пароль не может быть пустым, попробуйте еще раз.";

    /**
     * Проверяет корректность имени пользователя и пароля.
     *
     * @param name     имя пользователя.
     * @param password пароль пользователя.
     * @throws ValidationInputDataException если имя или пароль пустые.
     */
    public static void validate(String name, String password) throws ValidationInputDataException {
        if (name.trim().isEmpty()) {
            throw new ValidationInputDataException(EMPTY_NAME_MESSAGE);
        }

        if (password.trim().isEmpty()) {
            throw new ValidationInputDataException(EMPTY_PASSWORD_MESSAGE);
        }
    }
}
