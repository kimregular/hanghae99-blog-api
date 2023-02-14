package com.sparta.hanghaeblogserver.dto;

import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class BlogResponseDto {

    private String title;
    private String writer;
    private String content;
    private LocalDateTime createAt;

    public BlogResponseDto(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.createAt = blog.getCreateAt();
    }

    public BlogResponseDto(Blog blog, User user) {
        this.title = blog.getTitle();
        this.writer = user.getUsername();
        this.content = blog.getContent();
        this.createAt = blog.getCreateAt();
    }

}

/*
todos
1. 성공시 responseDto 반환
2. 실패시 status 코드 반환
-> apiResponseDto 필요

3. 유저id에서 유저 이름 가져오기 -> ask

 */

