package capstone3d.Server.domain.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateRequest {
    @NotBlank(message = "회원정보수정시 비밀번호는 필수입니다.")
    private String password;

    private String changePassword;

    private String checkChangePassword;

    private String nickname;

    private String business_name;
}
