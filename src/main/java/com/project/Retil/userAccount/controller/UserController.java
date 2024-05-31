package com.project.Retil.userAccount.controller;

import com.project.Retil.settings.security.JwtUtil;
import com.project.Retil.til.dto.TilListDTO;
import com.project.Retil.til.service.TilService;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.dto.*;
import com.project.Retil.userAccount.service.UserService;
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

    // 1. 사용자 회원가입
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

    // 2. 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        User_Information user = userService.login(loginRequest);
        if (user != null) {

            String token = jwtUtil.generateToken(user.getEmail(), user.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new TokenResponseDTO(token, user.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 3. 비밀번호 변경을 위한 이메일 인증
    @PostMapping("/auth")
    public ResponseEntity<String> requestPwChange(@RequestBody String email) {
        String sendCode = userService.sendMail(email);
        return sendCode != null ?
                ResponseEntity.status(HttpStatus.OK).body(sendCode) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 4. 비밀번호 변경
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

    // 5. 회원 삭제
    @DeleteMapping("/{user_id}/delete")
    public ResponseEntity<RedirectView> deleteUser(@PathVariable Long user_id,
                                                   @RequestBody String password) {
        User_Information deleted = userService.deleteUser(user_id, password);
        return deleted != null ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 6. 마이 페이지
    @GetMapping("/{user_id}/my_page")
    public MyPageDTO showMyPage(@PathVariable Long user_id) {
        User_Information user =  userService.findUser(user_id);
        return new MyPageDTO(
                user.getNickname(),
                user.getEmail(),
                user.getLatestPwChange()
        );
    }

    // 7. 닉네임 수정
    @PatchMapping("/{user_id}/my_page/nickname")
    public MyPageDTO nicknameChange(@PathVariable Long user_id,
                                    @RequestBody String newNickname) {
        User_Information user =  userService.findUser(user_id);
        return new MyPageDTO(
                newNickname,
                user.getEmail(),
                user.getLatestPwChange()
        );
    }

    @GetMapping("/main/{user_id}")
    public MainPageDTO showMain(@PathVariable Long user_id) {
        // 필요한 정보 : 오늘의 공부량, 총 공부량, 현재 티어, 현 소속 그룹 리스트(아직 없음), 과목별 TIL 리스트(최근 20개)(☆)
        // 1. user id 정보로 user 객체 찾기
        // 2. user 객체로 User_Rank, Group 리스트(아직 불가능), TIL 리스트 찾기(최근 20개만)
        // 3. 해당 정보를 MainPageDTO 객체에 저장
        // 4. 반환

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
}