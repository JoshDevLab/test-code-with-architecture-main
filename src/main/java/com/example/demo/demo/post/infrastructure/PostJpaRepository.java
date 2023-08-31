package com.example.demo.demo.post.infrastructure;

import com.example.demo.post.infrastructure.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

}