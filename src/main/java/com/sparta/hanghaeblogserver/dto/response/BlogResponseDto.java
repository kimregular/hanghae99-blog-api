package com.sparta.hanghaeblogserver.dto.response;

import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDto {
// userId = 1
// 다른 로직은 추가하지 않도록 한다.

    private String title;
    private String content;
    private LocalDateTime createAt;
    private String writer;

}

/*
todos
1. 성공시 responseDto 반환 -> 객체
2. 실패시 status 코드 반환 -> 문자열
-> apiResponseDto 필요

 */

