package com.sparta.hanghaeblogserver.service;

import com.sparta.hanghaeblogserver.dto.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.BlogResponseDto;
import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.entity.User;
import com.sparta.hanghaeblogserver.jwt.JwtUtil;
import com.sparta.hanghaeblogserver.repository.BlogRepository;
import com.sparta.hanghaeblogserver.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BlogResponseDto createMemo(BlogRequestDto requestDto, HttpServletRequest request) {
        // request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시물 작성 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰 에러");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO로 DB에 저장할 객체 만들기
            Blog blog = blogRepository.saveAndFlush(new Blog(requestDto, user));
            return new BlogResponseDto(blog);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getMemos() {
        List<Blog> blogList = blogRepository.findAllByOrderByCreateAtDesc();
        List<BlogResponseDto> responseDtos = new ArrayList<>();
        for (Blog blog : blogList) {
            responseDtos.add(new BlogResponseDto(blog));
        }
        return responseDtos;
    }

    @Transactional
    public BlogResponseDto getMemo(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물없음")
        );
        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto update(Long id, BlogRequestDto requestDto, HttpServletRequest request) {
        // request에서 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시물 업데이트 가능
        if (token != null) {
            // 토큰 검증
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰 에러");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
            );

            blog.update(requestDto);

            return new BlogResponseDto(blog);
        }
        return null;

    }

    @Transactional
    public Long delete(Long id, HttpServletRequest request) {

        // request에서 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시물 업데이트 가능
        if (token != null) {
            // 토큰 검증
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰 에러");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
            );

            blogRepository.delete(blog);
            return id;
        }
        return null;
    }


}
