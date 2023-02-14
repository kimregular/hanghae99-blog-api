package com.sparta.hanghaeblogserver.controller;

import com.sparta.hanghaeblogserver.dto.LoginRequestDto;
import com.sparta.hanghaeblogserver.dto.SignupRequestDto;
import com.sparta.hanghaeblogserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    @PostMapping("/signup")
    public HttpEntity<String> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

        userService.signup(signupRequestDto);
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        userService.login(loginRequestDto);
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

}
