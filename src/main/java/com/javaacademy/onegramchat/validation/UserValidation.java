package com.javaacademy.onegramchat.validation;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.InvalidPasswordException;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;
import com.javaacademy.onegramchat.exceptions.UserNotFoundException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;
import com.javaacademy.onegramchat.exceptions.ValidationInputDataException;

import java.util.Map;

public class UserValidation {

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
    public static void checkNotAvailableUsernameTo(String name, Map<String, User> users)
            throws UserRegistrationException {

        if (users.containsKey(name)) {
            throw new UserRegistrationException(USERNAME_TAKEN_MESSAGE);
        }
    }

    /**
     * Проверяет корректность учетных данных пользователя.
     *
     * @param name  имя пользователя.
     * @param users карта зарегистрированных пользователей.
     * @throws UserNotFoundException если пользователь не прошел авторизацию.
     */
    public static void checkAvailableUsernameTo(String name, Map<String, User> users)
            throws UserNotFoundException {

        if (!users.containsKey(name)) {
            throw new UserNotFoundException("Пользователь с именем " + name + " не зарегистрирован.");
        }
    }

    /**
     * Проверяет корректность учетных данных пользователя.
     *
     * @param user     пользователь.
     * @param password введенный пароль пользователя.
     * @throws InvalidPasswordException если пользователь не прошел авторизацию.
     */
    public static void checkVerifyingPassword(User user, String password)
            throws InvalidPasswordException {

        if (user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Неверный пароль для пользователя " + user.getName());
        }
    }

    /**
     * Проверяет, авторизован ли пользователь.
     *
     * @throws UserAuthorizationException если текущий пользователь не авторизован.
     */
    public static void checkUserAuthorization(User currentUser) throws UserAuthorizationException {
        if (currentUser == null) {
            throw new UserAuthorizationException("Вы не авторизованы!");
        }
    }

    public static void checkSystemOccupiedAnotherUser(User currentUser) throws UserAuthorizationException {
        if (currentUser != null) {
            throw new UserAuthorizationException("Авторизация невозможна!\n" +
                    "Система уже занята пользователем по имени " + currentUser.getName());
        }
    }
}
