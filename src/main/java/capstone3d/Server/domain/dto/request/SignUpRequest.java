package capstone3d.Server.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String email;

    private String password;

    private String nickname;

    private String phone;

    private String business_name;
}