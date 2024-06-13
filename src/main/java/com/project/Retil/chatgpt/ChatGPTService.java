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
import com.project.Retil.userAccount.Entity.User_Information;

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

    public List<Question> generateQuestions(Til til, User_Information user) {
        String prompt =
            "Create a question and an answer based on the following content (한국어로). Format: Q: [Question] A: [Answer] "
                + til.getContent();

        try {
            OpenAIRequest request = new OpenAIRequest(prompt);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("Content-Type", "application/json");

            HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<OpenAIResponse> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                OpenAIResponse.class
            );

            if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
                return response.getBody().getChoices().stream()
                    .map(choice -> {
                        String[] qa = choice.getMessage().getContent().split("A: ");
                        String questionContent = qa[0].replace("Q: ", "").trim();
                        String answerContent = qa[1].trim();
                        return new Question(questionContent, answerContent, til, user);
                    })
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
