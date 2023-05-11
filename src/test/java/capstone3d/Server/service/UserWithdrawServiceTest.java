package capstone3d.Server.service;

import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.repository.UserRepository;
import capstone3d.Server.response.StatusMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserWithdrawServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 탈퇴 성공")
    void withdrawSuccess(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when
        userService.withdraw("1234");

        //then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.login(LoginRequestForTest.LoginRequest("A@A.com","1234")));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Login_Fail);
    }

    @Test
    @DisplayName("비밀번호 불일치로 회원 탈퇴 실패")
    void withdrawFailByUnCorrectPassword(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when & then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.withdraw("123"));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Not_Match_Password);
    }
}