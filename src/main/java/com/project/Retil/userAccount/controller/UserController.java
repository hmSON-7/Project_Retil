package com.project.Retil.userAccount.controller;

import com.project.Retil.settings.security.JwtUtil;
import com.project.Retil.til.dto.TilListDTO;
import com.project.Retil.til.service.TilService;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.dto.*;
import com.project.Retil.userAccount.service.UserService;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TilService tilService;
    private final JwtUtil jwtUtil;

    /**
     * 1. 사용자 회원가입
     * @param joinRequest 웹으로부터 유저의 닉네임, 이메일, 비밀번호, 비밀번호 재입력 값을 받음
     * @return 회원가입에 성공한 경우 리다이렉트 주소를 설정하고 웹으로 HttpStatus.OK 및 주소 반환
     */
    @PostMapping("/join")
    public ResponseEntity<RedirectView> join(@RequestBody JoinRequestDTO joinRequest) {
        User_Information newUser = userService.signUp(joinRequest);
        if (newUser != null) {
            RedirectView redirectView = new RedirectView("/login", true);
            return ResponseEntity.status(HttpStatus.OK).body(redirectView);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 2. 사용자 로그인
     * @param loginRequest 사용자가 로그인 할 때 입력하는 이메일, 비밀번호를 웹으로부터 받음
     * @return 로그인 실패시 BAD_REQUEST 반환, 로그인 성공 시 JWT 토큰 생성 후 토큰과 유저 번호를 웹으로 반환
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        User_Information user = userService.login(loginRequest);
        if (user != null) {

            String token = jwtUtil.generateToken(user.getEmail(), user.getId());
            return ResponseEntity.status(HttpStatus.OK)
                .body(new TokenResponseDTO(token, user.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 3. 비밀번호 변경 요청
     * 입력된 이메일이 DB에 등록되어 있으면 서버에서 해당 이메일로 6자리 랜덤 코드 전송
     * @param email 비밀번호 변경 요청의 대상 이메일을 웹으로부터 받음
     * @return 성공시 대상 이메일로 코드 전송, 실패시 BAD_REQUEST 반환
     */
    @PostMapping("/auth")
    public ResponseEntity<String> requestPwChange(@RequestBody String email) {
        String sendCode = userService.sendMail(email);
        return sendCode != null ?
            ResponseEntity.status(HttpStatus.OK).body(sendCode) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 4. 비밀번호 변경 로직
     * 이메일 인증 성공시 비밀번호 변경, 변경된 값을 유저 DB에 저장
     * @param email 요청 대상 이메일
     * @param password 변경하고자 하는 신규 비밀번호
     * @return 해당 유저에 대한 기존 DB 정보를 가져와 새 비밀번호를 저장한 후 다시 DB에 저장
     */
    @PostMapping("/{email}/pw_change")
    public ResponseEntity<RedirectView> pwChange(@PathVariable String email,
        @RequestBody String password) {
        User_Information user = userService.pwChange(email, password);
        if (user != null) {
            RedirectView redirectView = new RedirectView("/login", true);
            return ResponseEntity.status(HttpStatus.OK).body(redirectView);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 5. 회원 계정 삭제
     * 유저 정보 확인 후 DB에서 계정 제거
     * @param user_id 계정 삭제를 요청한 유저 번호. 해당 번호로 유저 객체 탐색
     * @param password 계정 삭제 전 유저 인증을 위한 비밀번호 입력
     * @return DB에서 계정 정보 삭제 후 반환
     */
    @DeleteMapping("/{user_id}/delete")
    public ResponseEntity<RedirectView> deleteUser(@PathVariable Long user_id,
        @RequestBody String password) {
        User_Information deleted = userService.deleteUser(user_id, password);
        return deleted != null ?
            ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 6. 회원 마이 페이지 출력
     * @param user_id 접속 중인 유저 번호. 해당 번호로 유저 객체 탐색
     * @return 해당 유저의 닉네임, 이메일을 반환
     */
    @GetMapping("/{user_id}/my_page")
    public MyPageDTO showMyPage(@PathVariable Long user_id) {
        User_Information user = userService.findUser(user_id);
        return new MyPageDTO(
            user.getNickname(),
            user.getEmail()
        );
    }

    /**
     * 7. 닉네임 수정
     * @param user_id 접속 중인 유저 번호. 해당 번호로 유저 객체 탐색
     * @param newNickname 변경하고자 하는 신규 닉네임
     * @return DB에서 가져온 user 객체의 닉네임 변경 후 다시 DB에 저장 및 반환
     */
    @PatchMapping("/{user_id}/my_page/nickname")
    public ResponseEntity<MyPageDTO> nicknameChange(@PathVariable Long user_id,
                                                    @RequestBody String newNickname) {
        User_Information updatedUser = userService.changeNickname(user_id, newNickname);
        if (updatedUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new MyPageDTO(
                    updatedUser.getNickname(),
                    updatedUser.getEmail()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 8. 메인 페이지
     * 출력 정보 : 오늘의 공부량, 총 공브량, 현재 티어, 과목별 TIL 리스트
     * @param user_id 접속 중인 유저 번호. 해당 번호로 유저 객체 탐색
     * @return User 객체, User_Rank 객체, TIL 객체 리스트 생성 후 반환
     */
    @GetMapping("/main/{user_id}")
    public MainPageDTO showMain(@PathVariable Long user_id) {
        // 필요한 정보 : 오늘의 공부량, 총 공부량, 현재 티어, 현 소속 그룹 리스트, 과목별 TIL 리스트(최근 20개)(☆)
        // 1. user id 정보로 user 객체 찾기
        // 2. user 객체로 User_Rank, Group 리스트, TIL 리스트 찾기(최근 20개만)
        // 3. 해당 정보를 MainPageDTO 객체에 저장
        // 4. 반환

        //** 그룹 리스트는 웹에서 별개로 찾을 예정

        User_Information user = userService.findUser(user_id);
        User_Rank userRank = userService.findUserRank(user);
        ArrayList<TilListDTO> tilList = tilService.showList(user_id);

        return new MainPageDTO(
            userRank.getTodayStudyTime(),
            userRank.getTotalStudyTime(),
            userRank.getUserRank(),
            tilList
        );
    }

    /**
     * 9. 유저 랭킹 페이지
     * 유저 전체의 랭크 정보를 조회하고 시간 순으로 정렬하여 반환한다
     * @return 모든 유저의 랭크 정보를 리스트로 반환
     */
    @GetMapping("/rank")
    public List<UserRankDTO> showRankList() {
        return userService.showRankList();
    }

    /**
     * 10. 개인 랭킹 페이지
     * 유저 본인의 랭크 정보를 별도로 조회한다
     * @param user_id 접속 중인 유저 번호. 해당 번호로 유저 객체 탐색
     * @return 유저에 대한 랭크 정보를 찾아 닉네임, 랭킹, 총 공부량 순서대로 반환
     */
    @GetMapping("/rank/{user_id}")
    public UserRankDTO showMyRank(@PathVariable Long user_id) {

        User_Information user = userService.findUser(user_id);
        User_Rank userRank = userService.findUserRank(user);

        return new UserRankDTO(
                user.getNickname(),
                userRank.getUserRank(),
                userRank.getTotalStudyTime()
        );
    }
}