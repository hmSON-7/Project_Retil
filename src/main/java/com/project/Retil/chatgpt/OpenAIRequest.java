package com.project.Retil.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIRequest {

    private String model; // 사용할 모델 ID
    private List<Message> messages; // 요청에 포함될 메시지 목록
    private int max_tokens; // 최대 토큰 수

    // 프롬프트를 기반으로 OpenAIRequest 객체를 생성하는 생성자입니다.
    public OpenAIRequest(String prompt) {
        this.model = "gpt-3.5-turbo-16k"; // 사용할 모델 ID를 "gpt-3.5-turbo"로 설정
        this.messages = List.of(
                new Message("user", prompt)); // 메시지 목록에 사용자 역할과 프롬프트 내용이 포함된 Message 객체 추가
        this.max_tokens = 3000; // 최대 토큰 수를 5000으로 설정
    }

    @Getter
    @AllArgsConstructor
    public static class Message {

        private String role; // 메시지의 역할
        private String content; // 메시지의 내용
    }
}
