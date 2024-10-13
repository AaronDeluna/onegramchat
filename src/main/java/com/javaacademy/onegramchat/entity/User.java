package com.javaacademy.onegramchat.entity;

import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class User {
    String name;
    String password;
    List<Message> messages = new ArrayList<>();

    public void addMessage(@NonNull Message message) {
        messages.add(message);
    }
}