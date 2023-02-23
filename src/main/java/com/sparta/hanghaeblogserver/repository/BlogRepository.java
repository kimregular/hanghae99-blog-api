package com.sparta.hanghaeblogserver.repository;

import com.sparta.hanghaeblogserver.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BlogRepository extends JpaRepository<Blog, Long> {


    Optional<Blog> findByIdAndUserId(Long id, Long userId);
}
