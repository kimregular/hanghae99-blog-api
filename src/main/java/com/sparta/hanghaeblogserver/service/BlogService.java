package com.sparta.hanghaeblogserver.service;

import com.sparta.hanghaeblogserver.dto.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.BlogResponseDto;
import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Blog createMemo(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return blog;
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getMemos() {
        List<Blog> blogList = blogRepository.findAllByOrderByCreateAtDesc();
        List<BlogResponseDto> responseDtos = new ArrayList<>();
        for (Blog blog : blogList) {
            responseDtos.add(new BlogResponseDto(blog));
        }
        return responseDtos;
    }

    @Transactional
    public BlogResponseDto getDetailMemo(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물없음")
        );
        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물 없음")
        );
        if (validatePassword(blog.getPassword(), requestDto.getPassword())) {
            blog.update(requestDto);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 오류");
        }
        return new BlogResponseDto(blog);

    }

    @Transactional
    public Long delete(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물 없음"));
        if (validatePassword(blog.getPassword(), requestDto.getPassword())) {
            blogRepository.delete(blog);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 오류");
        }

        return id;
    }


    private boolean validatePassword(String tmpPwd, String memoPsw) {
        return tmpPwd.equals(memoPsw);
    }


}
