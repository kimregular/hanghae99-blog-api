package com.sparta.hanghaeblogserver.controller;

import com.sparta.hanghaeblogserver.dto.request.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.response.ResponseDto;
import com.sparta.hanghaeblogserver.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/posts")
    public ResponseDto<?> createMemo(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.createMemo(requestDto, request);
    }

    @GetMapping("/posts")
    public ResponseDto<?> getMemos() {

        return blogService.getMemos();
    }


    @GetMapping("/posts/{id}")
    public ResponseDto<?> getMemo(@PathVariable Long id) {

        return blogService.getMemo(id);
    }

    @PutMapping("/posts/{id}")
    public ResponseDto<?> updateMemo(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {

        return blogService.update(id, requestDto, request);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseDto<?> deleteMemo(@PathVariable Long id, HttpServletRequest request) {
        return blogService.delete(id, request);

    }
}
