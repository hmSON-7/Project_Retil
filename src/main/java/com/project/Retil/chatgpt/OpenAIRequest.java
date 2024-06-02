package com.project.Retil.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OpenAIRequest {

    private String model;
    private List<Message> messages;

    public OpenAIRequest(String prompt) {
        this.model = "gpt-3.5-turbo"; // 사용할 모델 ID
        this.messages = List.of(new Message("user", prompt));
    }

    @Getter
    @AllArgsConstructor
    public static class Message {

        private String role;
        private String content;
    }
}
