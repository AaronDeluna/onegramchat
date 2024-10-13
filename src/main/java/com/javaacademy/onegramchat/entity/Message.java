package com.javaacademy.onegramchat.entity;

import lombok.Value;

@Value
public class Message {
    String text;
    MessageType type;
    String from;
    String to;
}