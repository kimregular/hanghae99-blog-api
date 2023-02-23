package com.sparta.hanghaeblogserver.controller;

import com.sparta.hanghaeblogserver.dto.request.LoginRequestDto;
import com.sparta.hanghaeblogserver.dto.request.SignupRequestDto;
import com.sparta.hanghaeblogserver.dto.response.ResponseDto;
import com.sparta.hanghaeblogserver.dto.response.UserResponseDto;
import com.sparta.hanghaeblogserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseDto<UserResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }


    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

}
