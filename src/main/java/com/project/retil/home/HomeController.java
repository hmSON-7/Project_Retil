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

    /**
     * <pre>
     * 세션에 로그인 정보가 없으면 home View 렌더링
     * 세션에 로그인한 정보가 있다면 loginHome 렌더링
     * </pre>
     *
     * @param userSessionDto 세션을 통해 가져온 멤버 정보로 만약 로그인 하지 않았으면 null
     * @return 로그인한 사용자인 경우 loginHome, 로그인 하지 않은 사용자인 경우 home
     */
    @GetMapping("/")
    public String home(@Login UserSessionDTO userSessionDto, Model model) {
        log.debug("[{}] 홈페이지", MDC.get(LogConst.TRACE_ID));

        if (userSessionDto == null) {
            return "greetings";
        }

        model.addAttribute("UserSessionDto", userSessionDto);

        return "index";
    }
}