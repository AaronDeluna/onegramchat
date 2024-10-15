package com.javaacademy.onegramchat.validation;

import lombok.Value;

@Value
public class InputMessageData {

    String recipientName;
    String messageText;
}
