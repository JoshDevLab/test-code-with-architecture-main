package com.example.demo.post.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class PostTest {

    @Test
    public void PostCreate_으로_게시물을_만들_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloWorld!")
                .build();


         User writer = User.builder()
                 .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        ClockHolder testClockHolder = new TestClockHolder(1678530673958L);

        // when
        Post post = Post.from(writer, postCreate, testClockHolder);

        // then
        assertThat(post.getContent()).isEqualTo("helloWorld!");
        assertThat(post.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("kok202");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L);
    }

    @Test
    public void PostUpdate_으로_게시물을_수정_할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("Change HelloWorld!")
                .build();

        User writer = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        Post post = Post.builder()
                .id(1L)
                .content("helloworld!")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .content("helloWorld!")
                .writer(writer)
                .build();

        ClockHolder testClockHolder = new TestClockHolder(1678530673958L);

        // when
        post = post.update(postUpdate, testClockHolder);

        // then
        assertThat(post.getContent()).isEqualTo("Change HelloWorld!");
        assertThat(post.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("kok202");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }
}
