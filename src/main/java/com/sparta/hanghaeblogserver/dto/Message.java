package com.sparta.hanghaeblogserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message <T> {
    private int status;
    private String message;
    private T data;

    public Message(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
