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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capstone3d.Server.domain.Role.ROLE_ADMIN;
import static capstone3d.Server.domain.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisDao redisDao;

    @Transactional
    public UserResponse singUp(SignUpRequest signUpRequest) {
        boolean isExistId = userRepository
                .existsByIdentification(signUpRequest.getIdentification());

        boolean isExistNickname = userRepository
                .existsByNickname(signUpRequest.getNickname());

        if (isExistId) throw new BadRequestException("이미 존재하는 아이디입니다.");
        if (isExistNickname) throw new BadRequestException("이미 존재하는 닉네임입니다.");

        String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());

        User user;
        if (signUpRequest.getIdentification().equals("master")) {
            user = User.builder().identification(signUpRequest.getIdentification())
                    .password(encodePassword)
                    .name(signUpRequest.getName())
                    .nickname(signUpRequest.getNickname())
                    .phone(signUpRequest.getPhone())
                    .business_name(signUpRequest.getBusiness_name())
                    .role(ROLE_ADMIN)
                    .build();
        } else {
            user = User.builder().identification(signUpRequest.getIdentification())
                    .password(encodePassword)
                    .name(signUpRequest.getName())
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
                .findByIdentification(loginRequest.getIdentification())
                .orElseThrow(() -> new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));


        boolean matches = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword());
        if (!matches) throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");
        return UserResponse.of(user);
    }

    @Transactional
    public UpdateResponse update(UpdateRequest updateRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUser().getIdentification();
        User user = userRepository
                .findByIdentification(userId)
                .orElseThrow(() -> new BadRequestException("회원이 존재하지 않습니다."));

        if (updateRequest.getBusiness_name() != null && !updateRequest.getBusiness_name().equals("")) {
            user.updateBusiness_name(updateRequest.getBusiness_name());
        }
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().equals("")) {
            String encodePassword = passwordEncoder.encode(updateRequest.getPassword());
            user.updatePassword(encodePassword);
        }
        if (updateRequest.getNickname() != null && !updateRequest.getNickname().equals("")) {
            boolean isExistNickname = userRepository
                    .existsByNickname(updateRequest.getNickname());
            if (isExistNickname) throw new BadRequestException("이미 존재하는 닉네임입니다.");

            user.updateNickName(updateRequest.getNickname());
        }

        return new UpdateResponse(user.getNickname(), user.getBusiness_name());
    }

    @Transactional
    public void withdraw(String password) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getUser().getIdentification();
        User user = userRepository
                .findByIdentification(userId)
                .orElseThrow(() -> new BadRequestException("회원이 존재하지 않습니다."));

        boolean matches = passwordEncoder.matches(
                password,
                user.getPassword());

        if (!matches) throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");

        redisDao.deleteValues(userId);
        userRepository.delete(user);
    }
}