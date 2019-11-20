package com.example.springngblog.repository;

import com.example.springngblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUsername(String username);
}
