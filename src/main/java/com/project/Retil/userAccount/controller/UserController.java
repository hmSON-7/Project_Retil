package com.project.Retil.userAccount.controller;

import com.project.Retil.settings.security.JwtUtil;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;
import com.project.Retil.userAccount.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        User_Information user = userService.login(loginRequest);
        if (user != null) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}