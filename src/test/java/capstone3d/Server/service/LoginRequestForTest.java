package capstone3d.Server.service;

import capstone3d.Server.domain.dto.request.LoginRequest;

public class LoginRequestForTest {
    public static LoginRequest LoginRequest(String email, String password) {
        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}