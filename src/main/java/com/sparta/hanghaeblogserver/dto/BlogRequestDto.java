package com.sparta.hanghaeblogserver.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {

    private String title;
    private String writer;
    private String content;
    private String password;
}
