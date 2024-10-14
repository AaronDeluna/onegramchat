package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.CredentialsValidationException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;

import java.util.Map;

public class UserRegistrationValidator {

    /**
     * Проверяет возможность регистрации пользователя по имени.
     *
     * @param name имя пользователя для регистрации.
     * @param users карта зарегистрированных пользователей.
     * @return true, если имя пользователя доступно для регистрации.
     * @throws UserRegistrationException если имя пользователя уже занято.
     */
    public static boolean validateRegistration(String name, Map<String, User> users)
            throws UserRegistrationException {

        return UserExistenceValidator.validateUsernameIsAvailable(name, users);
    }
}
