package com.javaacademy.onegramchat.validation;


public class InputAuthorizationData {
    private final String name;
    private final String password;

    public InputAuthorizationData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
