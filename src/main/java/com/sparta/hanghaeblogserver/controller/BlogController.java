package com.sparta.hanghaeblogserver.controller;

import com.sparta.hanghaeblogserver.dto.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.BlogResponseDto;
import com.sparta.hanghaeblogserver.dto.Message;
import com.sparta.hanghaeblogserver.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
    public ResponseEntity<Message> deleteMemo(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        blogService.delete(id, requestDto, request);
        Message message = new Message(HttpStatus.OK.value(), "게시물 삭제 완료", null);
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
