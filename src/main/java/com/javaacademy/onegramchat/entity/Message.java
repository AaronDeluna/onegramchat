package com.javaacademy.onegramchat.entity;

import lombok.Value;

import java.util.List;

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
     *
     * Исходящие: выводится информация о отправителе.
     * Входящие: выводится информация о получателе.
     */
    public static void printMessages(List<Message> messages) {
        messages.forEach(message -> {
            if (message.getType() == MessageType.OUTCOMING) {
                System.out.printf("письмо от %s: %s \n", message.getFrom(), message.getText());
            } else if (message.getType() == MessageType.INCOMING) {
                System.out.printf("письмо к %s: %s \n", message.getTo(), message.getText());
            }
        });
    }
}