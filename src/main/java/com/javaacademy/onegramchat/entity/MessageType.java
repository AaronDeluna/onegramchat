package com.javaacademy.onegramchat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Тип сообщений
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum MessageType {

    INCOMING("Входящее"),
    OUTCOMING("Исходящее");

    String type;
}