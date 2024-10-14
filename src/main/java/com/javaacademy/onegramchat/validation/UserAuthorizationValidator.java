package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;

import java.util.Map;

public class UserAuthorizationValidator {

    /**
     * Проверяет корректность учетных данных пользователя.
     *
     * @param name     имя пользователя.
     * @param password пароль пользователя.
     * @param users    карта зарегистрированных пользователей.
     * @throws UserAuthorizationException если пользователь не прошел авторизацию.
     */
    public static void validateUserCredentials(String name, String password, Map<String, User> users)
            throws UserAuthorizationException {

        if (!users.containsKey(name)) {
            throw new UserAuthorizationException("Пользователь с именем " + name + " не зарегистрирован.");
        }

        if (!users.get(name).getPassword().equals(password)) {
            throw new UserAuthorizationException("Неверный пароль для пользователя " + name);
        }
    }
}
