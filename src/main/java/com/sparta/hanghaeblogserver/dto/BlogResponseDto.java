package com.sparta.hanghaeblogserver.dto;

import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class BlogResponseDto {
// userId = 1
// 다른 로직은 추가하지 않도록 한다.

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
1. 성공시 responseDto 반환 -> 객체
2. 실패시 status 코드 반환 -> 문자열
-> apiResponseDto 필요
-> 제네릭 설정으로 코드, 객체 반환하도록 설정
에러메시지, 에러메시지패스, 스테이터스
httpresponse body -> 스테이터스 출력하도록

3. 유저id에서 유저 이름 가져오기 -> ask
    response에서 repo 메서드를 사용해도 되는가? -> no

 */

