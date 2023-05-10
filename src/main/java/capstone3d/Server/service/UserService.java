package capstone3d.Server.service;

import capstone3d.Server.domain.dao.RedisDao;
import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.domain.dto.request.LoginRequest;
import capstone3d.Server.domain.dto.request.SignUpRequest;
import capstone3d.Server.domain.dto.request.UpdateRequest;
import capstone3d.Server.domain.dto.response.UpdateResponse;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.domain.User;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.repository.UserRepository;
import capstone3d.Server.response.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static capstone3d.Server.domain.Role.ROLE_ADMIN;
import static capstone3d.Server.domain.Role.ROLE_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisDao redisDao;
    private final S3UploadService s3UploadService;

    public UserResponse singUp(SignUpRequest signUpRequest) {
        boolean isExistId = userRepository
                .existsByEmail(signUpRequest.getEmail());

        if (isExistId) throw new BadRequestException(StatusMessage.Email_Duplicated);
        if (isExistNickname(signUpRequest.getNickname())) throw new BadRequestException(StatusMessage.Nickname_Duplicated);
//        회원가입 요청 검증 @valid 어노테이션 이용
//        if (signUpRequest.getBusiness_name() == null || signUpRequest.getNickname() == null ||
//                signUpRequest.getPassword() == null || signUpRequest.getPhone() == null || signUpRequest.getEmail() == null) {
//            throw new BadRequestException(StatusMessage.SignUp_Request_Error);
//        }

        String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());

        User user;
        if (signUpRequest.getEmail().equals("admin@admin.com")) {
            user = User.builder().email(signUpRequest.getEmail())
                    .password(encodePassword)
                    .nickname(signUpRequest.getNickname())
                    .phone(signUpRequest.getPhone())
                    .business_name(signUpRequest.getBusiness_name())
                    .role(ROLE_ADMIN)
                    .build();
        } else {
            user = User.builder().email(signUpRequest.getEmail())
                    .password(encodePassword)
                    .nickname(signUpRequest.getNickname())
                    .phone(signUpRequest.getPhone())
                    .business_name(signUpRequest.getBusiness_name())
                    .role(ROLE_USER)
                    .build();
        }

        userRepository.save(user);
        return UserResponse.of(user);
    }

    @Transactional(readOnly = true)
    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException(StatusMessage.Login_Fail));

        if (!checkPasswordMatching(loginRequest.getPassword(), user)) throw new BadRequestException(StatusMessage.Login_Fail);
        return UserResponse.of(user);
    }

    public UpdateResponse update(UpdateRequest updateRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUser().getEmail();
        User user = userRepository
                .findByEmail(userId)
                .orElseThrow(() -> new BadRequestException(StatusMessage.Not_Found_User));
        String password = updateRequest.getPassword();
        String changePassword = updateRequest.getChangePassword();
        String checkChangePassword = updateRequest.getCheckChangePassword();
        String businessName = updateRequest.getBusiness_name();
        String nickname = updateRequest.getNickname();

        if (!checkPasswordMatching(password, user)) throw new BadRequestException(StatusMessage.Not_Match_Password);    // 비밀번호 확인

        if (StringUtils.hasText(businessName)) {     // businessName 변경
            user.updateBusiness_name(updateRequest.getBusiness_name());
        }
        if (StringUtils.hasText(changePassword) || StringUtils.hasText(checkChangePassword)) {   // 비밀번호 변경
            if(!changePassword.equals(checkChangePassword))
                throw new BadRequestException(StatusMessage.Not_Match_Password);
            String encodePassword = passwordEncoder.encode(changePassword);
            user.updatePassword(encodePassword);
        }
        if (StringUtils.hasText(nickname)) {    // 닉네임 변경
            if (isExistNickname(nickname)) throw new BadRequestException(StatusMessage.Nickname_Duplicated);

            user.updateNickName(updateRequest.getNickname());
        }

        return new UpdateResponse(user.getNickname(), user.getBusiness_name());
    }

    public void withdraw(String password) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUser().getEmail();
        User user = userRepository
                .findByEmail(userId)
                .orElseThrow(() -> new BadRequestException(StatusMessage.Not_Found_User));

        if (!checkPasswordMatching(password, user)) throw new BadRequestException(StatusMessage.Not_Match_Password);

        s3UploadService.deleteFiles(user);
        redisDao.deleteValues(userId);
        userRepository.delete(user);
    }

    private boolean checkPasswordMatching(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    private boolean isExistNickname(String nickname) {  // 닉네임 중복검사
        return userRepository.existsByNickname(nickname);
    }
}