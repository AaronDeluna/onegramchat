package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;
import com.javaacademy.onegramchat.exceptions.ValidationInputDataException;
import com.javaacademy.onegramchat.validation.InputAuthorizationData;
import com.javaacademy.onegramchat.validation.UserAuthorizationValidator;
import com.javaacademy.onegramchat.validation.UserInputValidator;
import com.javaacademy.onegramchat.validation.UserNameValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OneGramChat {
    private static final Scanner scanner = new Scanner(System.in);
    private final Map<String, User> users = new HashMap<>();
    private User currentUser;

    /**
     * Создает нового пользователя, запрашивая имя и пароль.
     * <p>
     * Метод выполняет валидацию введенных данных и добавляет пользователя в систему.
     * При ошибках выводит сообщение и повторяет запрос.
     */
    public void createUser() {
        while (true) {
            InputAuthorizationData inputAuthorizationData = userInputData();
            try {
                UserNameValidator.validateUsernameIsAvailable(inputAuthorizationData.getName(), users);
                User user = new User(inputAuthorizationData.getName(), inputAuthorizationData.getPassword());
                users.put(inputAuthorizationData.getName(), user);
                System.out.println("Пользователь успешно зарегистрировался под именем: " +
                        inputAuthorizationData.getName());
                currentUser = user;
                break;
            } catch (UserRegistrationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Авторизует пользователя, запрашивая имя и пароль.
     * <p>
     * Метод выполняет валидацию учетных данных и устанавливает текущего пользователя.
     * При ошибках выводит сообщение и повторяет запрос.
     */
    public void userLogin() {
        while (true) {
            InputAuthorizationData inputAuthorizationData = userInputData();
            try {
                User user = UserAuthorizationValidator.validateUserCredentials(inputAuthorizationData.getName(),
                        inputAuthorizationData.getPassword(), users);
                System.out.println("Вы успешно авторизовались");
                currentUser = user;
                break;
            } catch (UserAuthorizationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Выход текущего пользователя из системы.
     * <p>
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
     * Запрашивает у пользователя имя и пароль.
     * <p>
     * Метод выполняет валидацию введенных данных и возвращает их список, если данные корректны.
     * При ошибках выводит сообщение и повторяет запрос.
     *
     * @return InputAuthorizationData объект с введенными учетными данными.
     */
    public InputAuthorizationData userInputData() {
        while (true) {
            System.out.println("Введите имя пользователя: ");
            String name = scanner.nextLine().trim();
            System.out.println("Введите пароль: ");
            String password = scanner.nextLine().trim();
            try {
                UserInputValidator.validate(name, password);
                return new InputAuthorizationData(name, password);
            } catch (ValidationInputDataException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
