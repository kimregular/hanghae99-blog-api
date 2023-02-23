package com.sparta.hanghaeblogserver.controller;

import com.sparta.hanghaeblogserver.dto.request.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.response.BlogResponseDto;
import com.sparta.hanghaeblogserver.dto.response.ResponseDto;
import com.sparta.hanghaeblogserver.security.UserDetailsImpl;
import com.sparta.hanghaeblogserver.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/posts")
    public ResponseDto<?> createMemo(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.createMemo(requestDto, userDetails);
    }

    @GetMapping("/posts")
    public ResponseDto<List<BlogResponseDto>> getMemos() {

        return blogService.getMemos();
    }


    @GetMapping("/posts/{id}")
    public ResponseDto<BlogResponseDto> getMemo(@PathVariable Long id) {

        return blogService.getMemo(id);
    }

    @PutMapping("/posts/{id}")
    public ResponseDto<BlogResponseDto> updateMemo(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return blogService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseDto<String> deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.delete(id, userDetails);

    }
}
