package com.project.retil.home;

import com.project.retil.user.db.dto.UserSessionDTO;
import com.project.retil.settings.argument_resolver.Login;
import com.project.retil.settings.constant.LogConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 홈페이지와 관련된 컨트롤러입니다.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm.html";
    }
}