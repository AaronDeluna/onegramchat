package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;

import java.util.Map;

public class UserExistenceValidator {

    public static final String USERNAME_TAKEN_MESSAGE = "Данное имя пользователя уже занято, попробуйте еще раз.";

    /**
     * Проверяет, доступно ли имя пользователя для регистрации.
     *
     * @param name имя пользователя для проверки.
     * @param users карта зарегистрированных пользователей.
     * @return true, если имя пользователя доступно.
     * @throws UserRegistrationException если имя пользователя уже занято.
     */
    public static boolean validateUsernameIsAvailable(String name, Map<String, User> users) throws UserRegistrationException {
        if (users.containsKey(name)) {
            throw new UserRegistrationException(USERNAME_TAKEN_MESSAGE);
        }
        return true;
    }
}
