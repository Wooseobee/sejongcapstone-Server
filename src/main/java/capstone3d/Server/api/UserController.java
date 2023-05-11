package capstone3d.Server.api;

import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.domain.dto.request.LoginRequest;
import capstone3d.Server.domain.dto.request.SignUpRequest;
import capstone3d.Server.domain.dto.request.UpdateRequest;
import capstone3d.Server.domain.dto.response.TokenResponse;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.jwt.JwtTokenProvider;
import capstone3d.Server.response.AllResponse;
import capstone3d.Server.response.StatusMessage;
import capstone3d.Server.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-up")
    public AllResponse singUp(
            @RequestBody @Valid SignUpRequest signUpRequest
    ) {
        return new AllResponse(StatusMessage.Sign_Up_Success.getStatus(), StatusMessage.Sign_Up_Success.getMessage(), 1, userService.singUp(signUpRequest));
    }

    @PostMapping("/login")
    public AllResponse login(
            @RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response
    ) throws JsonProcessingException {
        UserResponse userResponse = userService.login(loginRequest);
        TokenResponse tokenResponse = jwtTokenProvider.createTokensByLogin(userResponse);
        response.addHeader("atk", tokenResponse.getAtk());
        response.addHeader("rtk", tokenResponse.getRtk());
        return new AllResponse(StatusMessage.Login_Success.getStatus(), StatusMessage.Login_Success.getMessage(), 1, userResponse);
    }

    @GetMapping("/reissue")
    public AllResponse reissue(
            @AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response
    ) throws JsonProcessingException {
        if (Objects.isNull(userDetails)) throw new BadRequestException(StatusMessage.Refresh_Token_Unauthorized);
        UserResponse userResponse = UserResponse.of(userDetails.getUser());
        TokenResponse tokenResponse = jwtTokenProvider.reissueAtk(userResponse);
        response.addHeader("atk", tokenResponse.getAtk());
        return new AllResponse(StatusMessage.Reissue_Token_Success.getStatus(), StatusMessage.Reissue_Token_Success.getMessage(), 0, null);
    }

    @PutMapping("/user")
    public AllResponse update(
            @RequestBody @Valid UpdateRequest updateRequest
    ) {
        return new AllResponse(StatusMessage.Update_Success.getStatus(), StatusMessage.Update_Success.getMessage(), 1, userService.update(updateRequest));
    }

    @PostMapping("/user")
    public AllResponse withdraw(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> passwordMap
    ) {
        if (Objects.isNull(userDetails)) throw new BadRequestException(StatusMessage.Unauthorized);
        userService.withdraw(passwordMap.get("password"));
        return new AllResponse(StatusMessage.Withdraw_Success.getStatus(), StatusMessage.Withdraw_Success.getMessage(), 0, null);
    }
}