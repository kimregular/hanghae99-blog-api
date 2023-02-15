package com.sparta.hanghaeblogserver.dto;

import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class BlogResponseDto {

    private String title;
    private String content;
    private LocalDateTime createAt;
    private String writer;


    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.createAt = blog.getCreateAt();
        this.writer = blog.getUser().getUsername();
    }


}

/*
todos
1. 성공시 responseDto 반환
2. 실패시 status 코드 반환
-> apiResponseDto 필요
 */

