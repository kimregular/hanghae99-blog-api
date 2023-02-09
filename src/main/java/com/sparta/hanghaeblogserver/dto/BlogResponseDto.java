package com.sparta.hanghaeblogserver.dto;

import com.sparta.hanghaeblogserver.entity.Blog;
import lombok.Getter;


@Getter
public class BlogResponseDto {

    private String title;
    private String writer;
    private String content;

    public BlogResponseDto(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public BlogResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.writer = blog.getWriter();
        this.content = blog.getContent();
    }

}
