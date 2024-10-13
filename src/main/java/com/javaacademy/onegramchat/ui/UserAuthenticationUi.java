package com.javaacademy.onegramchat.ui;

import com.javaacademy.onegramchat.entity.User;

import java.util.Scanner;

public class UserAuthenticationUi {
    private static Scanner scanner = new Scanner(System.in);

    public static User inputUserCredentials() {
        System.out.println("Введите имя пользователя: ");
        String name = scanner.nextLine().trim();

        System.out.println("Введите пароль: ");
        String password = scanner.nextLine().trim();

        return new User(name, password);
    }
}
