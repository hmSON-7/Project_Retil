package com.project.retil.settings.interceptor;

import com.project.retil.settings.constant.LogConst;
import com.project.retil.settings.constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 사용자의 로그인 상태 체크. HandlerInterceptor 인터페이스의 구현 클래스
 */
@Slf4j
public class UserLoginCheckInterceptor implements HandlerInterceptor {

    /**
     * 핸들러 호출 전 실행
     * 요청 URI와 추적 ID를 로그로 출력, 세션 체크
     * 세션에 로그인 정보가 없다면 사용자를 리다이렉트, 요청 중단
     * 세션에 로그인 정보가 있다면 요청 처리를 계속 진행
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.debug("[{}] [{}] 멤버 로그인 체크 인터셉터 실행", MDC.get(LogConst.TRACE_ID), requestURI);

        HttpSession session = request.getSession(false);

        // 세션에 멤버 값이 없다면 미인증 사용자
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            log.info("[{}] 미인증 사용자 요청", MDC.get(LogConst.TRACE_ID));

            // 로그인 페이지로 Redirect
            // 마지막에 요청한 URL 주소를 쿼리 스트링으로 같이 전달
            if (!requestURI.equals("/login")) {
                response.sendRedirect("/login?redirectURL=" + requestURI);
            }

            // 다음 체이닝 진행 안함
            return false;
        }

        // 로그인 한 유저의 요청
        return true;
    }
}
