package capstone3d.Server.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    private String nickname;

    @NotNull(message = "휴대폰 번호는 공백일 수 없습니다.")
    private String phone;

    @NotNull(message = "상호명은 공백일 수 없습니다.")
    private String business_name;
}