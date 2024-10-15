package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.Message;
import com.javaacademy.onegramchat.entity.MessageType;
import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.*;
import com.javaacademy.onegramchat.validation.InputAuthorizationData;
import com.javaacademy.onegramchat.validation.InputMessageData;
import com.javaacademy.onegramchat.validation.MessageValidation;
import com.javaacademy.onegramchat.validation.UserValidation;

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
            System.out.println("-------Регистрация нового пользователя-------");
            InputAuthorizationData inputAuthorizationData = userInputData();
            try {
                UserValidation.checkNotAvailableUsernameTo(inputAuthorizationData.getName(), users);
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
            System.out.println("-------Авторизация пользователя-------");
            InputAuthorizationData inputAuthorizationData = userInputData();
            try {
                UserValidation.checkAvailableUsernameTo(inputAuthorizationData.getName(), users);
                UserValidation.checkVerifyingPassword(inputAuthorizationData.getName(),
                        inputAuthorizationData.getPassword(), users);
                currentUser = users.get(inputAuthorizationData.getName());
                System.out.println("Вы успешно авторизовались");
                break;
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage() + "\nЧтобы продолжить зарегистрируйтесь!");
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
        System.out.println("-------Выход пользователя-------");
        try {
            checkUserAuthorization();
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
                UserValidation.inputDataValidate(name, password);
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
        System.out.println("-------Отправка сообщения-------");
        try {
            checkUserAuthorization();
            InputMessageData inputMessageData = inputMessage();
            User recipientUser = findUserByName(inputMessageData.getRecipientName(), users);
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
        while (true) {
            System.out.println("Введите имя получателя сообщения: ");
            String recipientName = scanner.nextLine().trim();
            System.out.println("Введите текст: ");
            String messageText = scanner.nextLine().trim();
            try {
                MessageValidation.massageInputValidation(recipientName, messageText);
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
    private void checkUserAuthorization() throws UserAuthorizationException {
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
    private User findUserByName(String userName, Map<String, User> users) throws UserNotFoundException {
        UserValidation.checkAvailableUsernameTo(userName, users);
        return users.get(userName);
    }

    /**
     * метод "Прочитать письма":
     * Выводит все письма текущего пользователя.     *
     * если текущего пользователя нет, то возникает ошибка: вы не авторизованы
     */
    public void readMessage() {
        System.out.println("-------Список сообщений-------");
        try {
            checkUserAuthorization();
            for (Message message : currentUser.getMessages()) {
                if (message.getType() == MessageType.OUTCOMING) {
                    System.out.printf("письмо от %s: %s \n", message.getFrom(), message.getText());
                } else if (message.getType() == MessageType.INCOMING) {
                    System.out.printf("письмо к %s: %s \n", message.getTo(), message.getText());
                } else {
                    System.out.println("У вас нет доступных сообщений");
                }
            }
        } catch (UserAuthorizationException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  метод "запуска чата":
     * чат постоянно ожидает команд из консоли:
     * "войти" - запуск функции "войти пользователю"
     * "новый" - запуск функции "создать пользователя"
     * "выйти" - запуск функции "выйти пользователю"
     * "написать" - запуск функции "написать письмо"
     * "прочитать" - запуск функции "прочитать письмо"
     * "exit" - окончание работы программы
     */
    public void startChat() {
        System.out.println("Мы приветствуем вас в нашем чате Gramchat!");
        while (true) {
            System.out.println("Варианты команд: войти, новый, выйти, написать, прочитать");
            System.out.println("Для выхода введите: exit");
            System.out.println("Введите команду чата:");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "войти" -> userLogin();
                case "новый" -> createUser();
                case "выйти" -> logout();
                case "написать" -> sendMessage();
                case "прочитать" -> readMessage();
                case "exit" -> {
                    System.out.println("Жаль что так быстро покидаете наш чат. До свидания!");
                    System.exit(0);
                }
                default -> System.out.println("Неверная команда. Повторите ввод.");
            }
        }
    }
}