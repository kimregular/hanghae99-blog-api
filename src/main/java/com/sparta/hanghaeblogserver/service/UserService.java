package com.sparta.hanghaeblogserver.service;

import com.sparta.hanghaeblogserver.dto.request.LoginRequestDto;
import com.sparta.hanghaeblogserver.dto.request.SignupRequestDto;
import com.sparta.hanghaeblogserver.dto.response.ResponseDto;
import com.sparta.hanghaeblogserver.dto.response.UserResponseDto;
import com.sparta.hanghaeblogserver.entity.User;
import com.sparta.hanghaeblogserver.entity.UserRoleEnum;
import com.sparta.hanghaeblogserver.jwt.JwtUtil;
import com.sparta.hanghaeblogserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
    public ResponseDto<UserResponseDto> signup(SignupRequestDto signupRequestDto) {

        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(signupRequestDto.getUsername());

        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 회원 이름입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }


        User user = User.builder()
            .username(signupRequestDto.getUsername())
            .password(password)
            .email(signupRequestDto.getEmail())
            .role(role)
            .build();
        userRepository.save(user);
        return ResponseDto.success(UserResponseDto.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .build());
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
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return ResponseDto.success(UserResponseDto.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .build()
        );
    }

}
