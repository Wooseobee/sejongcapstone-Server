package capstone3d.Server.service;

import capstone3d.Server.domain.dto.request.LoginRequest;
import capstone3d.Server.domain.dto.request.SignUpRequest;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.repository.UserRepository;
import capstone3d.Server.response.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static capstone3d.Server.service.SignUpRequestForTest.signUpRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional
public class UserLoginServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        //given
        SignUpRequest request = signUpRequest("a@b.com","별명");
        LoginRequest loginRequest = LoginRequest("a@b.com","1234");

        //when
        UserResponse signUpResponse = userService.singUp(request);

        //then
        UserResponse loginResponse = assertDoesNotThrow(() -> userService.login(loginRequest));
        assertThat(signUpResponse.getId()).isEqualTo(loginResponse.getId());
        assertThat(signUpResponse.getEmail()).isEqualTo(loginResponse.getEmail());
        assertThat(signUpResponse.getNickname()).isEqualTo(loginResponse.getNickname());
        assertThat(signUpResponse.getBusiness_name()).isEqualTo(loginResponse.getBusiness_name());
        assertThat(signUpResponse.getPhone()).isEqualTo(loginResponse.getPhone());
    }

    @Test
    @DisplayName("아이디 오류로 로그인 실패")
    void loginFailByEmailUnCorrect() {
        //given
        SignUpRequest request = signUpRequest("a@b.com","별명");
        LoginRequest loginRequest = LoginRequest("a@b2.com","1234");

        //when
        userService.singUp(request);

        //then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.login(loginRequest));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Login_Fail);
    }

    @Test
    @DisplayName("비밀번호 오류로 로그인 실패")
    void loginFailByPasswordUnCorrect() {
        //given
        SignUpRequest request = signUpRequest("a@b.com","별명");
        LoginRequest loginRequest = LoginRequest("a@b.com","12345");

        //when
        userService.singUp(request);

        //then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.login(loginRequest));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Not_Match_Password);
    }

    private static LoginRequest LoginRequest(String email, String password) {
        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}