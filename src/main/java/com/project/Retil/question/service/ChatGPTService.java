/*
package com.project.Retil.question.service;

import com.project.Retil.til.entity.Til;
import com.project.Retil.question.entity.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTService {

    @Value("${chatgpt.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
        .baseUrl("https://api.openai.com/v1/completions")
        .defaultHeader("Authorization", "Bearer " + apiKey)
        .build();

    public List<Question> generateQuestions(Til til) {
        List<Question> questions = new ArrayList<>();
        String content = til.getTitle() + "\n" + til.getContent();
        String response = callChatGPTAPI(content);

        // 응답을 파싱하여 질문 추출
        String[] generatedQuestions = response.split("\n");
        for (String q : generatedQuestions) {
            questions.add(new Question(q, til));
        }
        return questions;
    }

    private String callChatGPTAPI(String content) {
        // WebClient를 사용하여 ChatGPT API 호출
        String response = webClient.post()
            .body(BodyInserters.fromValue(new ChatGPTRequest(content)))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        return response;
    }

    // ChatGPTRequest 클래스 추가
    public static class ChatGPTRequest {

        private final String model = "text-davinci-003";
        private final String prompt;
        private final int max_tokens = 100;
        private final int n = 1;
        private final String stop = ".";

        public ChatGPTRequest(String prompt) {
            this.prompt = prompt;
        }

        public String getModel() {
            return model;
        }

        public String getPrompt() {
            return prompt;
        }

        public int getMax_tokens() {
            return max_tokens;
        }

        public int getN() {
            return n;
        }

        public String getStop() {
            return stop;
        }
    }
}
*/
