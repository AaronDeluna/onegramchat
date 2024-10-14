package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.InvalidPasswordException;

import java.util.Map;

public class UserPasswordValidator {

    /**
     * Проверяет корректность пароля для указанного пользователя.
     *
     * @param name имя пользователя.
     * @param password пароль пользователя.
     * @param users карта зарегистрированных пользователей.
     * @return true, если пароль корректен.
     * @throws InvalidPasswordException если пользователь не найден или пароль неверный.
     */
    public static boolean validatePassword(String name, String password, Map<String, User> users)
            throws InvalidPasswordException {
        User user = users.get(name);

        if (user == null) {
            throw new InvalidPasswordException("Пользователь " + name + " не найден.");
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Неверный пароль для пользователя " + name + ", попробуйте еще раз!");
        }

        return true;
    }
}
