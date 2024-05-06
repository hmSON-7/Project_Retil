package com.project.Retil.settings.argument_resolver;

import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.settings.constant.LogConst;
import com.project.Retil.settings.constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * JoinRequestDTO 파라미터에 Login 어노테이션이 있는 경우 적용되는 Argument Resolver
 * HandlerMethodArgumentResolver 인터페이스의 구현 클래스
 */
@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * Argument Resolver가 주어진 파라미터를 지원하는지 여부 반환
     * 해당 메서드에서는 파라미터에 Login 어노테이션이 있는지, 파라미터의 타입이 JoinRequestDTO인지 확인
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.debug("[{}] UserSessionDto Support Parameter 실행", MDC.get(LogConst.TRACE_ID));

        // Login Annotation을 가지고 있는가?
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        // 파라미터 타입이 MemberSessionDto 인가?
        boolean hasUserSessionDtoType = JoinRequestDTO
                .class
                .isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasUserSessionDtoType;
    }

    /**
     * 실제 Argument 해석 메서드
     * HTTP 요청에서 세션을 가져오고 세션에서 JoinRequestDTO 객체를 가져옴(없으면 null 반환)
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.debug("[{}] UserSessionDto Resolve Argument 실행", MDC.get(LogConst.TRACE_ID));

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        // 세션에 값이 없음
        // 로그인 한 사용자의 요청이 아님
        if (session == null) {
            return null;
        }

        // 멤버 정보를 세션에서 추출 함
        return session.getAttribute(SessionConst.LOGIN_USER);
    }
}