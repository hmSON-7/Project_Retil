package com.project.Retil.userAccount.service;

import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.til.repository.TilSubjectRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.Repository.UserRankRepository;
import com.project.Retil.userAccount.Repository.UserRepository;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;
import com.project.Retil.userAccount.dto.UserRankDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRankRepository userRankRepository;
    private final TilSubjectRepository tilSubjectRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * 1. 사용자 회원 가입
     * DB에 저장된 비밀번호가 일치하지 않거나 이미 등록된 이메일인 경우 회원 가입 거부
     * 유저 정보 객체, 유저 랭크 객체, TIL 과목 객체를 생성하여 DB에 저장
     * @param joinRequestDto 웹으로부터 닉네임, 이메일, 비밀번호, 비밀번호 확인 값을 받아옴
     * @return DB에 저장된 유저 정보 객체를 반환
     */
    @Override
    @Transactional
    public User_Information signUp(JoinRequestDTO joinRequestDto) {
        // 비밀번호 확인. 이미 웹에서 구현 했으나, 테스트를 위해 임의로 작성
        if (!joinRequestDto.getPassword().equals(joinRequestDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User_Information checkUser = userRepository.findByEmail(joinRequestDto.getEmail()).orElse(null);
        if(checkUser != null) {
            throw new IllegalArgumentException("이미 존재하는 계정입니다.");
        }

        // 유저 객체를 DB에 저장
        User_Information user = new User_Information(
            joinRequestDto.getNickname(),
            joinRequestDto.getEmail(),
            passwordEncoder.encode(joinRequestDto.getPassword()) // 비밀번호 암호화
        );

        User_Information savedUser = userRepository.save(user);

        // 회원 가입과 동시에 유저 랭크 객체와 TIL 과목 객체를 DB에 저장
        User_Rank userRank = new User_Rank
            (savedUser, 0L, 0L, LocalDate.now(), "unRanked");
        userRankRepository.save(userRank);

        TilSubject tilSubject = new TilSubject(
            "folder1", user, "#FFFFFF", 0L
        );
        tilSubjectRepository.save(tilSubject);

        log.info("신규 유저가 회원 가입 하였습니다: " + savedUser.getEmail()); // 로그 추가
        return savedUser;
    }

    /**
     * 2. 사용자 로그인
     * DB에 해당 계정이 존재하지 않거나 비밀번호가 다르면 로그인 거부
     * 로그인 조건 충족 시 DB에서 찾은 유저 객체 반환
     * @param loginRequestDto 웹으로부터 이메일, 비밀번호 값을 받음
     * @return 이메일로 찾은 유저 객체를 반환
     */
    @Override
    public User_Information login(LoginRequestDTO loginRequestDto) {
        User_Information user = userRepository.findByEmail(loginRequestDto.getEmail()).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("계정이 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    /**
     * 3. 비밀번호 변경 요청 로직
     * 비 로그인 상태인 유저가 비밀번호를 변경하고자 하는 경우 이메일로 랜덤 코드를 발송
     * 계정 소유자만 확인할 수 있는 6자리 랜덤 코드를 부여하여 타인이 비밀번호를 바꾸는 상황을 방지
     * @param email 웹에서 받는 이메일 값
     * @return 무작위 생성된 6자리 코드를 웹으로 반환하여 웹에서 코드 비교
     */
    @Override
    @Transactional
    public String sendMail(String email) {
        // 이메일 찾기
        User_Information findUser = userRepository.findByEmail(email).orElse(null);
        if (findUser == null) {
            throw new IllegalArgumentException("해당 이메일로 등록된 계정이 없습니다.");
        }

        // 존재하는 계정일 경우 해당 계정으로 이메일 발신
        return emailService.sendSimpleMessage(email);
    }

    /**
     * 4. 비밀번호 변경
     * DB에서 유저 객체를 가져와 비밀번호 업데이트 후 다시 저장
     * @param email 변경 대상 계정
     * @param newPassword 변경하려는 새 비밀번호
     * @return 암호화된 새 비밀번호로 변경한 후 DB에 다시 저장
     */
    @Override
    @Transactional
    public User_Information pwChange(String email, String newPassword) {
        User_Information user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("해당 계정이 존재하지 않습니다.");
        }

        user.changePassword(passwordEncoder.encode(newPassword));

        return userRepository.save(user);
    }

    /**
     * 5. 사용자 계정 삭제
     * DB로부터 해당 유저 객체를 찾아 비밀번호 인증 후 DB에서 삭제
     * @param user_id 접속 중인 유저 번호
     * @param password 비밀번호 확인
     * @return DB에서 해당 유저 정보 삭제 후 객체 반환
     */
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

    /**
     * 6. 회원 정보 반환
     * @param user_id 찾고자 하는 유저 번호
     * @return 유저 번호에 해당하는 유저 객체 반환
     */
    @Override
    public User_Information findUser(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    /**
     * 7. 회원 유저 랭크 정보 반환
     * @param user 찾고자 하는 유저 객체
     * @return 해당 유저에 대한 랭크 정보 반환
     */
    @Override
    public User_Rank findUserRank(User_Information user) {
        return userRankRepository.findByUser(user);
    }

    /**
     * 8. 회원 닉네임 변경
     * @param user_id 요청을 보낸 유저 번호
     * @param newNickname 바꾸고자 하는 신규 닉네임
     * @return DB에서 유저 객체를 가져와 닉네임 변경 후 다시 DB에 저장
     */
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

    /**
     * 9. 유저 랭크 리스트 반환
     * 모든 유저의 랭킹을 리스트로 반환
     * DB에서 모든 유저의 랭크 객체를 리스트로 가져와 각 유저에 대한 닉네임, 유저 랭크, 총 공부량 반환
     * @return UserRankDTO : 리스트 { 유저의 닉네임, 유저 랭크(문자열), 총 공부량 }
     * 해당 리스트는 총 공부량 기준 내림차순으로 반환
     */
    @Override
    public ArrayList<UserRankDTO> showRankList() {
        List<User_Rank> rankList = userRankRepository.findAll();

        // 닉네임, 랭크, 총 공부량을 리스트로 저장
        ArrayList<UserRankDTO> userRankList = new ArrayList<>();
        for(User_Rank userRank : rankList) {
            userRankList.add(new UserRankDTO(
                    userRank.getUser().getNickname(),
                    userRank.getUserRank(),
                    userRank.getTotalStudyTime()
            ));
        }

        // 총 공부량 기준 내림차순 정렬(큰 것부터)
        userRankList.sort(Comparator.comparing(UserRankDTO::getTotalTime).reversed());

        return userRankList;
    }
}
