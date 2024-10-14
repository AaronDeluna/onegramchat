package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.CredentialsValidationException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;

import java.util.Map;

public class UserRegistrationValidator {

    /**
     * Проверяет корректность регистрации пользователя.
     *
     * @param name имя пользователя.
     * @param password пароль пользователя.
     * @param users карта зарегистрированных пользователей.
     * @return true, если регистрация успешна.
     * @throws CredentialsValidationException если имя или пароль некорректны.
     * @throws UserRegistrationException если имя пользователя уже занято.
     */
    public static boolean validateRegistration(String name, String password, Map<String, User> users)
            throws CredentialsValidationException, UserRegistrationException {

        return UserInputValidator.validateCredentials(name, password)
                && UserExistenceValidator.validateUsernameIsAvailable(name, users);
    }
}
