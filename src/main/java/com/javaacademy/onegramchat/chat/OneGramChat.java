package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.Message;
import com.javaacademy.onegramchat.entity.MessageType;
import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;
import com.javaacademy.onegramchat.exceptions.ValidationInputDataException;
import com.javaacademy.onegramchat.validation.*;

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
                UserDataValidation.usernameIsAvailableValidate(inputAuthorizationData.getName(), users);
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
                UserDataValidation.userAuthorizationValidate(inputAuthorizationData.getName(),
                        inputAuthorizationData.getPassword(), users);
                currentUser = users.get(inputAuthorizationData.getName());
                System.out.println("Вы успешно авторизовались");
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
                UserDataValidation.inputDataValidate(name, password);
                return new InputAuthorizationData(name, password);
            } catch (ValidationInputDataException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Метод отправки сообщения
     * Вводится имя пользователя, вводится текст письма.
     * У текущего пользователя записывается в список сообщений как исходящий
     * У пользователя которому пишем, записывается в список сообщений как входящее
     * если такого пользователя нет, то возникает ошибка: такого пользователя нет
     * если текущего пользователя нет, то возникает ошибка: вы не авторизованы
     */
    public void SendMessage() {
        if (currentUser != null) {
            while (true) {
                System.out.println("Введите имя получателя сообщения: ");
                String recipientName = scanner.nextLine().trim();
                User recipientUser = selectUser(recipientName);

                System.out.println("Введите текст: ");
                String messageText = scanner.nextLine().trim();

                currentUser.addMessage(new Message(messageText, MessageType.OUTCOMING, currentUser.getName(),
                        recipientUser.getName()));
                recipientUser.addMessage(new Message (messageText, MessageType.INCOMING, currentUser.getName(),
                        recipientUser.getName()));
            }
        } else {
            System.out.println("Вы не авторизованы");
        }

    }

    /**
     * Метод выбора пользователя по имени из списка
     * Метод выполняет валидацию имени пользователя и возвращает пользователя.
     * При ошибке выводит сообщение
     */
    public User selectUser (String userName) {
        try {
            UserDataValidation.usernameIsAvailableValidate(userName, users);
            return users.get(userName);
        } catch (UserRegistrationException e) {
            throw new RuntimeException(e);
        }
    }
}
