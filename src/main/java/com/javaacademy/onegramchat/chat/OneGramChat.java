package com.javaacademy.onegramchat.chat;

import com.javaacademy.onegramchat.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OneGramChat {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);

    public void createUser() {
        System.out.println("Введите имя пользователя: ");
        String name = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        if (users.containsKey(name)) {
            System.out.println("Данное имя пользователя уже занято!");
        } else {
            users.put(name, new User(name, password));
            System.out.println("Пользователь " + name + " создан!");
        }
    }
}
