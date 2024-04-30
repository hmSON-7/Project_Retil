package com.project.retil.settings.interceptor;

import com.project.retil.settings.constant.LogConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * HTTP 요청과 응답을 로그로 출력하는 인터셉터. HandlerInterceptor 인터페이스의 구현 클래스
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    /**
     * 핸들러가 호출되기 전에 실행
     * 요청 URI와 랜덤 UUID를 로그로 출력, MDC에 추적을 위한 ID 설정
     * 항시 true 반환
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // MDC에 추적을 위한 ID를 설정합니다.
        MDC.put(LogConst.TRACE_ID, uuid);

        log.debug("REQUEST [{}][{}][{}]", uuid, requestURI, handler);

        return true;
    }

    /**
     * 핸들러 호출 후 뷰가 렌더링되기 전에 실행
     * MDC에서 추적 ID를 가져와 로그로 출력
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        // MDC에서 TRACE_ID를 통해 uuid를 가져옵니다.
        log.debug("postHandle [{}][{}]", MDC.get(LogConst.TRACE_ID), modelAndView);
    }

    /**
     * 요청 처리가 완전히 끝난 후 실행
     * 응답과 관련된 로그 출력, 예외 발생시 에러 로그 출력
     * MDC에서 추적 ID 제거
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();

        // MDC에서 TRACE_ID를 통해 uuid를 가져옵니다.
        log.debug("RESPONSE [{}][{}][{}]", MDC.get(LogConst.TRACE_ID), requestURI, handler);

        // 예외가 발생한 경우 ERROR 레벨의 로그를 출력합니다.
        if (ex != null) {
            log.error("afterCompletion error !! [{}]", MDC.get(LogConst.TRACE_ID), ex);
        }

        // MDC에서 TRACE_ID를 제거합니다.
        MDC.remove(LogConst.TRACE_ID);
    }
}