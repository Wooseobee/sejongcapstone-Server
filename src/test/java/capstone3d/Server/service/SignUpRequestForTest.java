package capstone3d.Server.service;

import capstone3d.Server.domain.dto.request.SignUpRequest;

public class SignUpRequestForTest {
    public static SignUpRequest signUpRequest(String email, String nickname) {
        return SignUpRequest.builder()
                .email(email)
                .password("1234")
                .nickname(nickname)
                .phone("010-2134-1234")
                .business_name("상호명")
                .build();
    }
}
