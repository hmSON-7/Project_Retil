package com.project.Retil.userAccount.service;

import com.project.Retil.userAccount.Repository.UserRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

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
        log.info("New user signed up: " + savedUser.getEmail());
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
    @Transactional
    public User_Information pwchange(String email) {
        Optional<User_Information> user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("해당 이메일로 등록된 계정이 없습니다.");
        }
        String token = emailService.sendSimpleMessage(email);
        user.ifPresentOrElse(token);
        return token;
    }
}
