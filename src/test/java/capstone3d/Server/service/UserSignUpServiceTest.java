package capstone3d.Server.service;

import capstone3d.Server.domain.dto.request.SignUpRequest;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.jwt.JwtTokenProvider;
import capstone3d.Server.repository.UserRepository;
import capstone3d.Server.response.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static capstone3d.Server.service.SignUpRequestForTest.signUpRequest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class UserSignUpServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("회원가입 요청 성공")
    void singUpSuccess() {
        //given
        SignUpRequest request = signUpRequest("a@b.com","별명");

        //when
        UserResponse userResponse = userService.singUp(request);

        //then
        assertThat(request.getEmail()).isEqualTo(userResponse.getEmail());
        assertThat(request.getPhone()).isEqualTo(userResponse.getPhone());
        assertThat(request.getNickname()).isEqualTo(userResponse.getNickname());
        assertThat(request.getBusiness_name()).isEqualTo(userResponse.getBusiness_name());
    }

    @Test
    @DisplayName("회원가입 이메일 중복")
    void singUpEmailDuplicated() {
        //given
        SignUpRequest request = signUpRequest("a@b.com","별명");

        //when
        userService.singUp(request);

        //then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.singUp(request));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Email_Duplicated);
    }

    @Test
    @DisplayName("회원가입 닉네임 중복")
    void singUpNicknameDuplicated() {
        //given
        SignUpRequest request1 = signUpRequest("a@b.com","별명");
        SignUpRequest request2 = signUpRequest("aa@b.com","별명");

        //when
        userService.singUp(request1);

        //then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.singUp(request2));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Nickname_Duplicated);
    }
}