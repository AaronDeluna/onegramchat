package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.Message;
import com.javaacademy.onegramchat.entity.MessageType;
import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.InvalidPasswordException;
import com.javaacademy.onegramchat.exceptions.MessageInputException;
import com.javaacademy.onegramchat.exceptions.NoMessagesException;
import com.javaacademy.onegramchat.exceptions.UserAuthorizationException;
import com.javaacademy.onegramchat.exceptions.UserNotFoundException;
import com.javaacademy.onegramchat.exceptions.UserRegistrationException;
import com.javaacademy.onegramchat.exceptions.ValidationInputDataException;
import com.javaacademy.onegramchat.validation.InputAuthorizationData;
import com.javaacademy.onegramchat.validation.InputMessageData;
import com.javaacademy.onegramchat.validation.MessageValidation;
import com.javaacademy.onegramchat.validation.UserValidation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.javaacademy.onegramchat.validation.UserValidation.checkSystemOccupiedAnotherUser;

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
                users.put(user.getName(), user);
                System.out.println("Пользователь успешно зарегистрировался под именем: " +
                        inputAuthorizationData.getName());
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
        try {
            checkSystemOccupiedAnotherUser(currentUser);
            System.out.println("-------Авторизация пользователя-------");
            InputAuthorizationData inputAuthorizationData = userInputData();
            User user = findUserByName(inputAuthorizationData.getName(), users);
            UserValidation.checkVerifyingPassword(user, inputAuthorizationData.getPassword());
            currentUser = user;
            System.out.println("Вы успешно авторизовались");
        } catch (UserAuthorizationException | UserNotFoundException | InvalidPasswordException e) {
            System.out.println(e.getMessage());
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
            UserValidation.checkUserAuthorization(currentUser);
            System.out.println("-------Выход пользователя-------");
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
    private InputAuthorizationData userInputData() {
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
        try {
            UserValidation.checkUserAuthorization(currentUser);
            System.out.println("-------Отправка сообщения-------");
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
     * Выводит список сообщений текущего пользователя.
     *
     */
    public void readMessage() {
        try {
            UserValidation.checkUserAuthorization(currentUser);
            System.out.println("-------Список сообщений-------");
            MessageValidation.verifyUserMessages(currentUser);
            currentUser.getMessages().forEach(Message::print);
        } catch (UserAuthorizationException | NoMessagesException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Основной метод чата. Обрабатывает команды пользователя:
     * - "войти" для авторизации,
     * - "новый" для регистрации,
     * - "выйти" для выхода из аккаунта,
     * - "написать" для отправки сообщения,
     * - "прочитать" для чтения сообщений,
     * - "exit" для выхода из чата.
     */
    public void startChat() {
        System.out.println("Мы приветствуем вас в нашем чате Gramchat!");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "войти" -> userLogin();
                case "новый" -> createUser();
                case "выйти" -> logout();
                case "написать" -> sendMessage();
                case "прочитать" -> readMessage();
                case "exit" -> exitChat();
                default -> System.out.println("Неверная команда. Повторите ввод.");
            }
        }
    }

    /**
     * Выводит меню команд чата на экран.
     *
     * <p>Отображает доступные команды и инструкцию для выхода.</p>
     */
    private void printMenu() {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("Варианты команд: войти, новый, выйти, написать, прочитать");
        System.out.println("Для выхода введите: exit");
        System.out.println("-".repeat(60));
        System.out.print("Введите команду чата: ");
    }


    /**
     * Завершает работу чата и выводит прощальное сообщение.
     *
     * <p>Метод завершает выполнение программы.</p>
     */
    private void exitChat() {
        System.out.println("Жаль, что так быстро покидаете наш чат. До свидания!");
        scanner.close();
        System.exit(0);
    }
}