package com.sparta.hanghaeblogserver.service;

import com.sparta.hanghaeblogserver.dto.request.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.response.BlogResponseDto;
import com.sparta.hanghaeblogserver.dto.response.ResponseDto;
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

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto<?> createMemo(BlogRequestDto requestDto, HttpServletRequest request) {
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
            Blog blog = Blog.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(user)
                .build();

            blogRepository.saveAndFlush(blog);

            return ResponseDto.success(
                BlogResponseDto.builder()
                    .title(blog.getTitle())
                    .content(blog.getContent())
                    .writer(user.getUsername())
                    .createAt(blog.getCreateAt())
                    .build()
            );


        }
        return ResponseDto.fail("BAD_REQUEST", "잘못된 요청입니다.");
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getMemos() {
        return ResponseDto.success(blogRepository.findAllByOrderByCreateAtDesc());
    }

    @Transactional
    public ResponseDto<?> getMemo(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물없음")
        );

        return ResponseDto.success(
            BlogResponseDto.builder()
                .title(blog.getTitle())
                .writer(blog.getUser().getUsername())
                .content(blog.getContent())
                .createAt(blog.getCreateAt())
                .build()
        );
    }

    @Transactional
    public ResponseDto<?> update(Long id, BlogRequestDto requestDto, HttpServletRequest request) {
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

            return ResponseDto.success(blog);
        }
        return null;

    }

    @Transactional
    public ResponseDto<?> delete(Long id, HttpServletRequest request) {

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
            if (!blog.getId().equals(user.getId())) {
                throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");

            } else {
                blogRepository.delete(blog);
                return ResponseDto.success("delete success");
            }
        }
        return null;
    }
}
