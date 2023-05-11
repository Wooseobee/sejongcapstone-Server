package capstone3d.Server.service;

import capstone3d.Server.domain.User;
import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.domain.dto.request.UpdateRequest;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserUpdateServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원정보 (비밀번호)수정 성공")
    void updatePasswordSuccess(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("1234")
                .changePassword("12345")
                .checkChangePassword("12345")
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when
        userService.update(updateRequest);
        User findUser = userRepository.findByEmail(loginResponse.getEmail()).get();

        //then
        assertThat(passwordEncoder.matches(updateRequest.getChangePassword(), findUser.getPassword())).isTrue();
        assertThat(passwordEncoder.matches(updateRequest.getPassword(), findUser.getPassword())).isFalse();
    }

    @Test
    @DisplayName("비밀번호가 달라 회원정보(비밀번호)수정 실패")
    void updatePasswordFailByUnCorrectPassword(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("123")
                .changePassword("12345")
                .checkChangePassword("12345")
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when & then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.update(updateRequest));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Not_Match_Password);
    }

    @Test
    @DisplayName("회원정보 변경비밀번호와 변경비밀번호 확인이 달라 (비밀번호)수정 실패")
    void updatePasswordFail(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("1234")
                .changePassword("12345")
                .checkChangePassword("1234")
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when & then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.update(updateRequest));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Not_Match_Password);
    }

    @Test
    @DisplayName("회원정보 (닉네임)수정 성공")
    void updateNicknameSuccess(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("1234")
                .nickname("변경된 닉네임")
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when
        userService.update(updateRequest);
        User findUser = userRepository.findByEmail(loginResponse.getEmail()).get();

        //then
        assertThat(loginResponse.getNickname()).isNotEqualTo(findUser.getNickname());
        assertThat(findUser.getNickname()).isEqualTo("변경된 닉네임");
    }

    @Test
    @DisplayName("닉네임 중복으로 회원정보 (닉네임)수정 실패")
    void updateNicknameFailByDuplicatedNickName(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("1234")
                .nickname("닉네임")
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when & then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.update(updateRequest));
        assertThat(exception.getStatusMessage()).isEqualTo(StatusMessage.Nickname_Duplicated);
    }

    @Test
    @DisplayName("회원정보 (상호명)수정 성공")
    void updateBusinessNameSuccess(){
        //given
        userService.singUp(SignUpRequestForTest.signUpRequest("A@A.com", "닉네임"));
        UserResponse loginResponse = userService.login(LoginRequestForTest.LoginRequest("A@A.com", "1234"));

        UpdateRequest updateRequest = UpdateRequest.builder()
                .password("1234")
                .business_name("변경된 상호명")
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginResponse.getEmail());
        Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        //when
        userService.update(updateRequest);
        User findUser = userRepository.findByEmail(loginResponse.getEmail()).get();

        //then
        assertThat(loginResponse.getBusiness_name()).isNotEqualTo(findUser.getBusiness_name());
        assertThat(findUser.getBusiness_name()).isEqualTo("변경된 상호명");
    }
}