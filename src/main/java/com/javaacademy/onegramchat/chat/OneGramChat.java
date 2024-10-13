package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.User;
import com.javaacademy.onegramchat.exceptions.InvalidUserException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OneGramChat {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);

    public void createUser(){
        String[] details = scannerSystem();
        String name = details[0];
        String password = details[1];

        if (users.containsKey(name)) {
            System.out.println("Данное имя пользователя уже занятно!");
        } else {
            users.put(name, new User(name, password));
            System.out.println("Пользователь " + name + " создан!");
        }
    }

    public boolean authenticate(String name, String password) {
        User user = users.get(name);
        return user != null && user.getPassword().equals(password);
    }

    public void login() throws InvalidUserException {
        String[] details = scannerSystem();
        String name = details[0];
        String password = details[1];

        if (authenticate(name, password)) {
            currentUser = users.get(name);
            System.out.println("Вы вошли в аккаунт");
        } else {
            throw new InvalidUserException("Неверное имя пользователя или пароль!");
        }
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("Пользователь " + currentUser.getName() + "вышел");
            currentUser = null;
        } else {
            System.out.println("Пользователь не вошел в систему");
        }
    }

    private String[] scannerSystem(){
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        return new String[] {name, password};
    }
}
