package com.sparta.hanghaeblogserver.service;

import com.sparta.hanghaeblogserver.dto.request.BlogRequestDto;
import com.sparta.hanghaeblogserver.dto.response.BlogResponseDto;
import com.sparta.hanghaeblogserver.dto.response.ResponseDto;
import com.sparta.hanghaeblogserver.entity.Blog;
import com.sparta.hanghaeblogserver.entity.User;
import com.sparta.hanghaeblogserver.entity.UserRoleEnum;
import com.sparta.hanghaeblogserver.jwt.JwtUtil;
import com.sparta.hanghaeblogserver.repository.BlogRepository;
import com.sparta.hanghaeblogserver.repository.UserRepository;
import com.sparta.hanghaeblogserver.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseDto<BlogResponseDto > createMemo(@RequestBody BlogRequestDto requestDto, UserDetailsImpl userDetails) {
        Blog blog = new Blog(requestDto, userDetails.getUser());
        blogRepository.save(blog);
        return ResponseDto.success(new BlogResponseDto(blog));
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<BlogResponseDto>> getMemos() {
        List<Blog> list = blogRepository.findAll();
        System.out.println(list);
        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();
        for (Blog blog : list) {
            blogResponseDtoList.add(new BlogResponseDto(blog));
        }
        return ResponseDto.success(blogResponseDtoList);
    }

    @Transactional
    public ResponseDto<BlogResponseDto> getMemo(Long id) {
        BlogResponseDto blogResponseDto = new BlogResponseDto(blogRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
        ));
        return ResponseDto.success(blogResponseDto);
    }

    @Transactional
    public ResponseDto<BlogResponseDto> update(Long id, BlogRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(blog.getUser().getUsername())) {
            blog.update(requestDto);
            return ResponseDto.success(new BlogResponseDto(blog));
        } else {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), "작성자만 수정할 수 있습니다.");
        }
    }

    @Transactional
    public ResponseDto<String> delete(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Blog blog = blogRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
        );

        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(blog.getUser().getUsername())) {
            blogRepository.delete(blog);
            return ResponseDto.success("delete complete");
        } else {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), "작성자만 삭제할 수 있습니다.");
        }

    }
}
