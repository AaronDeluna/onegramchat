package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.Message;
import com.javaacademy.onegramchat.entity.MessageType;
import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.*;
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
                System.out.println(e.getMessage() + "Чтобы продолжить зарегестрируйтесь:");
                createUser();
            } catch (InvalidPasswordException e) {
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
        try {
            isUserAuthenticated();
            System.out.println("Пользователь " + currentUser.getName() + " успешно вышел");
            currentUser = null;
        } catch (UserAuthorizationException e) {
            System.out.println(e.getMessage());
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
     * Отправляет сообщение пользователю.
     * Получает данные о сообщении от авторизованного пользователя, находит получателя и добавляет сообщение
     * как исходящее для отправителя и входящее для получателя.
     */
    public void sendMessage() {
        try {
            InputMessageData inputMessageData = inputMessage();
            User recipientUser = findUserByName(inputMessageData.getRecipientName());
            currentUser.addMessage(new Message(inputMessageData.getMessageText(),
                    MessageType.OUTCOMING, currentUser.getName(), recipientUser.getName()));
            recipientUser.addMessage(new Message(inputMessageData.getMessageText(),
                    MessageType.INCOMING, currentUser.getName(), recipientUser.getName()));
            System.out.println("Сообщение успешно отправлено пользователю: " + inputMessageData.getRecipientName());
        } catch (UserAuthorizationException | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Запрашивает у пользователя ввод данных сообщения, включая имя получателя и текст сообщения.
     * Проверяет, авторизован ли пользователь, и продолжает запрашивать данные до тех пор,
     * пока не будут введены корректные значения.
     *
     * @return объект InputMessageData, содержащий имя получателя и текст сообщения.
     * @throws UserAuthorizationException если пользователь не авторизован.
     */
    private InputMessageData inputMessage() throws UserAuthorizationException {
        isUserAuthenticated();
        while (true) {
            System.out.println("Введите имя получателя сообщения: ");
            String recipientName = scanner.nextLine().trim();
            System.out.println("Введите текст: ");
            String messageText = scanner.nextLine().trim();
            try {
                MessageDataValidation.massageInputValidation(recipientName, messageText);
                return new InputMessageData(recipientName, messageText);
            } catch (MessageInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Проверяет, авторизован ли пользователь.
     *
     * @throws UserAuthorizationException если текущий пользователь не авторизован.
     */
    private void isUserAuthenticated() throws UserAuthorizationException {
        if (currentUser == null) {
            throw new UserAuthorizationException("Вы не авторизованы!");
        }
    }

    /**
     * Находит пользователя по имени.
     *
     * @param userName имя пользователя для поиска.
     * @return найденный пользователь.
     * @throws UserNotFoundException если пользователь не найден.
     */
    private User findUserByName(String userName) throws UserNotFoundException {
        MessageDataValidation.userExistValidation(userName, users);
        return users.get(userName);
    }
}
