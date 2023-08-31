package com.example.demo.demo.user.infrastructure;

import com.example.demo.user.domain.UserStatus;
import java.util.Optional;

import com.example.demo.user.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<com.example.demo.user.infrastructure.UserEntity, Long> {

    Optional<com.example.demo.user.infrastructure.UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
