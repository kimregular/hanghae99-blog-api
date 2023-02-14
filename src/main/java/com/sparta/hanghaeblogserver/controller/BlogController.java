package com.sparta.hanghaeblogserver.controller;

import com.sparta.hanghaeblogserver.dto.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.BlogResponseDto;
import com.sparta.hanghaeblogserver.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/posts")
    public BlogResponseDto createMemo(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.createMemo(requestDto, request);
    }

    @GetMapping("/posts")
    public List<BlogResponseDto> getMemos() {

        return blogService.getMemos();
    }


    @GetMapping("/posts/{id}")
    public BlogResponseDto getMemo(@PathVariable Long id) {

        return blogService.getMemo(id);
    }

    @PutMapping("/posts/{id}")
    public BlogResponseDto updateMemo(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {

        return blogService.update(id, requestDto, request);
    }

    @DeleteMapping("/posts/{id}")
    public Long deleteMemo(@PathVariable Long id, HttpServletRequest request) {
        return blogService.delete(id, request);
    }
}
