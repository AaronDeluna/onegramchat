package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.CredentialsValidationException;
import com.javaacademy.onegramchat.exceptions.InvalidPasswordException;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;
import com.javaacademy.onegramchat.validation.UserAuthorizationValidator;
import com.javaacademy.onegramchat.validation.UserInputValidator;
import com.javaacademy.onegramchat.validation.UserRegistrationValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OneGramChat {
    private final Map<String, User> users = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;

    /**
     * Создает нового пользователя, запрашивая имя и пароль.
     *
     * Метод выполняет валидацию введенных данных и добавляет пользователя в систему.
     * При ошибках выводит сообщение и повторяет запрос.
     */
    public void createUser() {
        while (true) {
            User user = userAuthInput();
            try {
                if (UserRegistrationValidator.validateRegistration(user.getName(), user.getPassword(), users)) {
                    System.out.println("Пользователь успешно зарегестрировался под именем: " + user.getName());
                    users.put(user.getName(), user);
                    currentUser = user;
                    break;
                }
            } catch (CredentialsValidationException | UserRegistrationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Авторизует пользователя, запрашивая имя и пароль.
     *
     * Метод выполняет валидацию учетных данных и устанавливает текущего пользователя.
     * При ошибках выводит сообщение и повторяет запрос.
     */
    public void userLogin() {
        while (true) {
            User user = userAuthInput();
            try {
                if (UserAuthorizationValidator.validateUserCredentials(user.getName(), user.getPassword(), users)) {
                    System.out.println("Вы успешно авторизовались");
                    currentUser = user;
                    break;
                }
            } catch (InvalidPasswordException | UserAuthorizationException | CredentialsValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Выходит текущего пользователя из системы.
     *
     * Если пользователь авторизован, выводит сообщение о выходе и устанавливает текущего пользователя в null.
     * Если пользователь не авторизован, выводит сообщение об этом.
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("Пользователь " + currentUser.getName() + "вышел");
            currentUser = null;
        } else {
            System.out.println("Пользователь не вошел в систему");
        }
    }

    /**
     * Запрашивает у пользователя имя и пароль для авторизации.
     *
     * Метод выполняет валидацию введенных данных и возвращает объект User,
     * если данные корректны. При ошибках выводит сообщение и повторяет запрос.
     *
     * @return User объект с введенными учетными данными.
     */
    public User userAuthInput() {
        while (true) {
            System.out.println("Введите имя пользователя: ");
            String name = scanner.nextLine().trim();
            System.out.println("Введите пароль: ");
            String password = scanner.nextLine().trim();
            try {
                if (UserInputValidator.validateCredentials(name, password)) {
                    return new User(name, password);
                }
            } catch (CredentialsValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
