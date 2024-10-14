package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.exceptions.CredentialsValidationException;

public class UserInputValidator {

    public static final String EMPTY_NAME_MESSAGE = "Имя не может быть пустым, попробуйте еще раз.";
    public static final String EMPTY_PASSWORD_MESSAGE = "Пароль не может быть пустым, попробуйте еще раз.";

    /**
     * Проверяет корректность имени пользователя и пароля.
     *
     * @param name имя пользователя.
     * @param password пароль пользователя.
     * @return true, если данные корректны.
     * @throws CredentialsValidationException если имя или пароль пустые.
     */
    public static boolean validateCredentials(String name, String password) throws CredentialsValidationException {
        if (name.trim().isEmpty()) {
            throw new CredentialsValidationException(EMPTY_NAME_MESSAGE);
        }

        if (password.trim().isEmpty()) {
            throw new CredentialsValidationException(EMPTY_PASSWORD_MESSAGE);
        }
        return true;
    }
}
