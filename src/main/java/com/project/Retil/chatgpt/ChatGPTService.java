package com.project.Retil.chatgpt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.project.Retil.question.entity.Question;
import com.project.Retil.til.entity.Til;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatGPTService {

    private final RestTemplate restTemplate;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    public ChatGPTService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Question> generateQuestions(Til til) {
        String prompt = "Create a question based on the following content (한국어로): " + til.getContent();

        try {
            OpenAIRequest request = new OpenAIRequest(prompt);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("Content-Type", "application/json");

            HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<OpenAIResponse> response = restTemplate.exchange(
                    "https://api.openai.com/v1/chat/completions", // 수정된 URI
                    HttpMethod.POST,
                    entity,
                    OpenAIResponse.class
            );

            if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
                return response.getBody().getChoices().stream()
                        .map(choice -> new Question(choice.getMessage().getContent(), til))
                        .collect(Collectors.toList());
            } else {
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling OpenAI API: " + e.getMessage(), e);
        }
    }
}
