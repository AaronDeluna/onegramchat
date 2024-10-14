package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.InvalidPasswordException;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;

import java.util.Map;

public class UserAuthorizationValidator {

    /**
     * Проверяет корректность учетных данных пользователя.
     *
     * @param name имя пользователя.
     * @param password пароль пользователя.
     * @param users карта зарегистрированных пользователей.
     * @return true, если данные корректны.
     * @throws InvalidPasswordException если пароль неверный.
     * @throws UserAuthorizationException если пользователь не зарегистрирован.
     */
    public static boolean validateUserCredentials(String name, String password, Map<String, User> users)
            throws InvalidPasswordException, UserAuthorizationException {

        User user = users.get(name);
        if (user == null) {
            throw new UserAuthorizationException("Пользователь с именем " + name + " не зарегистрирован.");
        }

        if (!UserPasswordValidator.validatePassword(name, password, users)) {
            throw new InvalidPasswordException("Неверный пароль для пользователя " + name);
        }

        return true;
    }
}
