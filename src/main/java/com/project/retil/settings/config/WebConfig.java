package com.project.retil.settings.config;

import com.project.retil.settings.argument_resolver.LoginUserArgumentResolver;
import com.project.retil.settings.interceptor.LogInterceptor;
import com.project.retil.settings.interceptor.UserLoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 웹 MVC 설정 클래스. Interceptor와 ArgumentResolver 등록
 * WebMvcConfigurer 인터페이스의 구현 클래스
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 인터셉터 등록 메서드
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그 출력 인터셉터 등록
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        // 멤버 로그인 확인 인터셉터 등록
        registry.addInterceptor(new UserLoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/user/signUp",
                        "/user/login", "/", "/admin/**");
    }

    /**
     * Argument Resolver 등록 메서드
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }
}