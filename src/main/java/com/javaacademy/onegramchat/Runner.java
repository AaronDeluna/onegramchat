package com.javaacademy.onegramchat;

import com.javaacademy.onegramchat.chat.OneGramChat;

public class Runner {
    public static void main(String[] args) {
        OneGramChat oneGramChat = new OneGramChat();
        oneGramChat.createUser(); //1
        oneGramChat.logout();
        oneGramChat.createUser(); //2
        oneGramChat.userLogin(); //1
        oneGramChat.sendMessage();
    }
}
