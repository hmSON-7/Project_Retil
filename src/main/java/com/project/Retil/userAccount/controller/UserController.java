package com.project.Retil.userAccount.controller;

import com.project.Retil.settings.security.JwtUtil;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;
import com.project.Retil.userAccount.dto.TokenResponseDTO;
import com.project.Retil.userAccount.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
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
    public ResponseEntity<RedirectView> pwChange(@PathVariable Long email,
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
    public User_Information showMyPage(@PathVariable Long user_id) {
        return userService.findUser(user_id);
    }
}