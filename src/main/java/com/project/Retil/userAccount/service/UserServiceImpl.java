package com.project.Retil.userAccount.service;

import com.project.Retil.userAccount.Repository.UserRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User_Information signUp(JoinRequestDTO joinRequestDto) {
        if (!joinRequestDto.getPassword().equals(joinRequestDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User_Information user = new User_Information(
            joinRequestDto.getNickname(),
            joinRequestDto.getEmail(),
            passwordEncoder.encode(joinRequestDto.getPassword())
        );
        User_Information savedUser = userRepository.save(user);
        log.info("New user signed up: " + savedUser.getEmail()); // 로그 추가
        return savedUser;
    }

    @Override
    public User_Information login(LoginRequestDTO loginRequestDto) {
        User_Information user = userRepository.findByEmail(loginRequestDto.getEmail());

        if (user == null) {
            throw new RuntimeException("계정이 존재하지 않습니다.");
        }
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    @Override
    public User_Information pwchange(String email) {
        User_Information user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("해당 이메일로 등록된 계정이 없습니다.");
        }
        // 비밀번호 재설정 이메일 발송 로직 구현
        String token = UUID.randomUUID().toString(); // 임시 토큰 생성
        // 이메일 발송 로직 추가 필요

        // 예를 들어 비밀번호 변경 로직이 성공했다고 가정하고 user 반환
        return user;
    }

    @Override
    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }
}
