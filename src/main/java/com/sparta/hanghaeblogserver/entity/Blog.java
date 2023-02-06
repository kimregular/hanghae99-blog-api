package com.sparta.hanghaeblogserver.entity;

import com.sparta.hanghaeblogserver.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;

    public Blog(String title, String writer, String content, String password) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.password = password;
    }

    public Blog(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }
}
