package com.sparta.hanghaeblogserver.repository;

import com.sparta.hanghaeblogserver.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByCreateAtDesc();
}
