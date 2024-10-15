package com.javaacademy.onegramchat.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InputMessageData {
    private final String recipientName;
    private final String messageText;
}
