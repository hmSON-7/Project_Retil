package com.project.Retil.chatgpt;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIResponse {

    private List<Choice> choices; // OpenAI API의 응답에서 선택지 목록

    @Data
    public static class Choice {

        private Message message; // 선택지에 포함된 메시지
    }

    @Data
    public static class Message {

        private String role; // 메시지의 역할 (예: "user", "assistant")
        private String content; // 메시지의 내용
    }
}
