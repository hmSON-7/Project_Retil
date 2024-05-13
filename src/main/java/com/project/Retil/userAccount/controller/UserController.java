package com.project.Retil.userAccount.controller;

import com.project.Retil.settings.argument_resolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;
import com.project.Retil.userAccount.service.UserService;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.dto.UserSessionDTO;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	@PostMapping("/join")
	public ResponseEntity<RedirectView> join(@RequestBody JoinRequestDTO joinRequest) {
		User_Information newUser = userService.signUp(joinRequest);
		if (newUser != null ) {
			RedirectView redirectView = new RedirectView("/login", true);
			return ResponseEntity.status(HttpStatus.OK).body(redirectView);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/login")
	public ResponseEntity<User_Information> login(@RequestBody LoginRequestDTO loginRequest,
												  @Login UserSessionDTO session) {
		User_Information user = userService.login(loginRequest);

		return (user != null) ?
			ResponseEntity.status(HttpStatus.OK).body(user) :
		    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}