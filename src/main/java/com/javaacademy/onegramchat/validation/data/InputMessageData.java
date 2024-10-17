package com.javaacademy.onegramchat.validation.data;

import lombok.Value;

@Value
public class InputMessageData {

    String recipientName;
    String messageText;
}
