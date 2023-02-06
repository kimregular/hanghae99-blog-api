package com.sparta.hanghaeblogserver.service;

import com.sparta.hanghaeblogserver.dto.BlogRequestDto;
import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Blog> getMemos() {
        return blogRepository.findAllByOrderByCreateAtDesc();
    }
}
