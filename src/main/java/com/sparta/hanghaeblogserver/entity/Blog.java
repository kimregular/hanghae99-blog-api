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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String content;


    @Column(nullable = false)
    private Long userId;

    public Blog(BlogRequestDto requestDto, Long userId) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.userId = userId;
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
