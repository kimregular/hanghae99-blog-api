package com.sparta.hanghaeblogserver.controller;

import com.sparta.hanghaeblogserver.dto.BlogRequestDto;
import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/posts")
    public Blog createMemo(@RequestBody BlogRequestDto requestDto) {
        return blogService.createMemo(requestDto);
    }

    @GetMapping("/posts")
    public List<Blog> getMemos() {
        return blogService.getMemos();
    }


    @GetMapping("/posts/{id}")
    public Blog getDetailMemo(@PathVariable Long id) {
        return blogService.getDetailMemo(id);
    }

    @PutMapping("/posts/{id}")
    public Blog updateMemo(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.update(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public Blog deleteMemo(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.delete(id, requestDto);
    }
}
