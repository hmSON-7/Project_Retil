package com.project.Retil.userAccount.service;

import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.til.repository.TilSubjectRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.Repository.UserRankRepository;
import com.project.Retil.userAccount.Repository.UserRepository;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRankRepository userRankRepository;
    private final TilSubjectRepository tilSubjectRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 1. 사용자 회원가입
    @Override
    @Transactional
    public User_Information signUp(JoinRequestDTO joinRequestDto) {
        // 비밀번호 확인. 이미 웹에서 구현 했으나, 테스트를 위해 임의로 작성
        if (!joinRequestDto.getPassword().equals(joinRequestDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 유저 객체를 DB에 저장
        User_Information user = new User_Information(
            joinRequestDto.getNickname(),
            joinRequestDto.getEmail(),
            passwordEncoder.encode(joinRequestDto.getPassword())
        );

        User_Information savedUser = userRepository.save(user);

        User_Rank userRank = new User_Rank
            (savedUser, 0L, 0L, LocalDate.now(), "unRanked");
        userRankRepository.save(userRank);

        TilSubject tilSubject = new TilSubject(
            "folder1", user, Color.WHITE, 0L
        );
        tilSubjectRepository.save(tilSubject);

        log.info("신규 유저가 회원 가입 하였습니다: " + savedUser.getEmail()); // 로그 추가
        return savedUser;
    }

    // 2. 사용자 로그인
    @Override
    public User_Information login(LoginRequestDTO loginRequestDto) {
        Optional<User_Information> findUser = userRepository.findByEmail(
            loginRequestDto.getEmail());

        if (findUser.isEmpty()) {
            throw new RuntimeException("계정이 존재하지 않습니다.");
        }
        User_Information user = findUser.get();
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    // 3. 비밀번호 변경 요청. 요청시 이메일로 코드 전송
    @Override
    @Transactional
    public String sendMail(String email) {
        // 이메일 찾기
        Optional<User_Information> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) {
            throw new RuntimeException("해당 이메일로 등록된 계정이 없습니다.");
        }

        // 존재하는 계정일 경우 해당 계정으로 이메일 발신
        return emailService.sendSimpleMessage(email);
    }

    // 4. 비밀번호 변경(미완성)
    @Override
    @Transactional
    public User_Information pwChange(String email, String password) {
        Optional<User_Information> requestedUser = userRepository.findByEmail(email);
        if (requestedUser.isEmpty()) {
            return null;
        }

        User_Information user = requestedUser.get();
        user = new User_Information(
            user.getNickname(),
            user.getEmail(),
            passwordEncoder.encode(password)
        );

        return userRepository.save(user);
    }

    // 5. 사용자 계정 삭제
    @Override
    @Transactional
    public User_Information deleteUser(Long user_id, String password) {
        User_Information target = findUser(user_id);
        if (target == null) {
            return null;
        }

        if (!passwordEncoder.matches(target.getPassword(), password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(target);
        return target;
    }

    // 6. 회원 정보 반환
    @Override
    public User_Information findUser(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    @Override
    public User_Rank findUserRank(User_Information user) {
        return userRankRepository.findByUser(user);
    }

    // 7. 닉네임 변경
    @Override
    @Transactional
    public User_Information changeNickname(Long user_id, String newNickname) {
        User_Information user = findUser(user_id);
        if (user != null) {
            user.changeNickname(newNickname);
            userRepository.save(user);
        }
        return user;
    }
}
