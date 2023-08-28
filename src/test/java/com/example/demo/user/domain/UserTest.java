package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @Test
    public void User는_UserCreate_객체로_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@kakao.com")
                .address("Gyeongi")
                .nickname("kok202-k")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaaaaa-aaa-aaaaaaaaaa-aaab"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("kok202@kakao.com");
        assertThat(user.getAddress()).isEqualTo("Gyeongi");
        assertThat(user.getNickname()).isEqualTo("kok202-k");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaa-aaa-aaaaaaaaaa-aaab");
    }
    @Test
    public void User는_UserUpdate_객체로_업데이트_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .address("Seoul")
                .nickname("kok202-k")
                .build();

        // when
        user = user.update(userUpdate);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("kok202@kakao.com");
        assertThat(user.getAddress()).isEqualTo("Seoul");
        assertThat(user.getNickname()).isEqualTo("kok202-k");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }
    @Test
    public void User_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        // when
        user = user.login(new TestClockHolder(1678530673958L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }
    @Test
    public void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Pangyo")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        // when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
    @Test
    public void 잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Pangyo")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            user.certificate("aaaaaaaa-aaaa-aaaa-aaaa");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
