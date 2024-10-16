package com.javaacademy.onegramchat.entity;

import lombok.Value;

@Value
public class Message {
    String text;
    MessageType type;
    String from;
    String to;

    /**
     * Выводит список сообщений пользователя.
     *
     * <p>Сообщения делятся на исходящие и входящие:</p>
     * <p>
     * Исходящие: выводится информация об отправителе.
     * Входящие: выводится информация о получателе.
     */
    public void print() {
        if (type.equals(MessageType.OUTCOMING)) {
            System.out.printf("письмо от %s: %s \n", from, text);
        }
        if (type.equals(MessageType.INCOMING)) {
            System.out.printf("письмо к %s: %s \n", to, text);
        }
    }
}