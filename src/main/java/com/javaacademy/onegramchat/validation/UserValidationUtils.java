package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;
import com.javaacademy.onegramchat.exceptions.ValidationInputDataException;

import java.util.Map;

public class UserValidationUtils {
    public static final String EMPTY_NAME_MESSAGE = "Имя не может быть пустым, попробуйте еще раз.";
    public static final String EMPTY_PASSWORD_MESSAGE = "Пароль не может быть пустым, попробуйте еще раз.";
    public static final String USERNAME_TAKEN_MESSAGE = "Данное имя пользователя уже занято, попробуйте еще раз.";

    /**
     * Проверяет корректность имени пользователя и пароля.
     *
     * @param name     имя пользователя.
     * @param password пароль пользователя.
     * @throws ValidationInputDataException если имя или пароль пустые.
     */
    public static void inputDataValidate(String name, String password) throws ValidationInputDataException {
        if (name.trim().isEmpty()) {
            throw new ValidationInputDataException(EMPTY_NAME_MESSAGE);
        }

        if (password.trim().isEmpty()) {
            throw new ValidationInputDataException(EMPTY_PASSWORD_MESSAGE);
        }
    }

    /**
     * Проверяет доступность имени пользователя для регистрации.
     *
     * @param name  имя пользователя для проверки.
     * @param users карта зарегистрированных пользователей.
     * @throws UserRegistrationException если имя пользователя уже занято.
     */
    public static void usernameIsAvailableValidate(String name, Map<String, User> users)
            throws UserRegistrationException {
        if (users.containsKey(name)) {
            throw new UserRegistrationException(USERNAME_TAKEN_MESSAGE);
        }
    }

    /**
     * Проверяет корректность учетных данных пользователя.
     *
     * @param name     имя пользователя.
     * @param password пароль пользователя.
     * @param users    карта зарегистрированных пользователей.
     * @throws UserAuthorizationException если пользователь не прошел авторизацию.
     */
    public static void userAuthorizationValidate(String name, String password, Map<String, User> users)
            throws UserAuthorizationException {

        if (!users.containsKey(name)) {
            throw new UserAuthorizationException("Пользователь с именем " + name + " не зарегистрирован.");
        }

        if (!users.get(name).getPassword().equals(password)) {
            throw new UserAuthorizationException("Неверный пароль для пользователя " + name);
        }
    }
}
