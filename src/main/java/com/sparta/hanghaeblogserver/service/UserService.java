package com.sparta.hanghaeblogserver.service;

import com.sparta.hanghaeblogserver.dto.request.LoginRequestDto;
import com.sparta.hanghaeblogserver.dto.request.SignupRequestDto;
import com.sparta.hanghaeblogserver.dto.response.ResponseDto;
import com.sparta.hanghaeblogserver.dto.response.UserResponseDto;
import com.sparta.hanghaeblogserver.entity.User;
import com.sparta.hanghaeblogserver.jwt.JwtUtil;
import com.sparta.hanghaeblogserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public ResponseDto<UserResponseDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return ResponseDto.fail("DUPLICATED_USERNAME", "중복된 이름입니다.");
        }

        User user = User.builder()
            .username(signupRequestDto.getUsername())
            .password(signupRequestDto.getPassword())
            .email(signupRequestDto.getEmail())
            .build();

        userRepository.save(user);

         ResponseDto responseDto = ResponseDto.success(
            UserResponseDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build()
        );
        return responseDto;
    }



    @Transactional(readOnly = true)
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        return ResponseDto.success(
            UserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build()
        );
    }

}
