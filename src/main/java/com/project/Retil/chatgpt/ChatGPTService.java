package com.project.Retil.chatgpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Retil.question.entity.Question;
import com.project.Retil.question.repository.QuestionRepository;
import com.project.Retil.til.entity.Til;
import com.project.Retil.userAccount.Entity.User_Information;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatGPTService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final QuestionRepository questionRepository;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    public List<Question> generateAndSaveQuestions(Til til, User_Information user) {
        String prompt = createPromptFromTilContent(til.getContent());

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
                List<Question> questions = parseQuestions(
                        response.getBody().getChoices().get(0).getMessage().getContent(), til, user);

                // 저장된 질문 목록을 데이터베이스에 저장합니다.
                questionRepository.saveAll(questions);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling OpenAI API: " + e.getMessage(), e);
        }

        return null;
    }

    private String createPromptFromTilContent(String tilContentJson) {
        StringBuilder promptBuilder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode root = objectMapper.readTree(tilContentJson);
            for (JsonNode node : root) {
                if (node.has("text")) {
                    promptBuilder.append(node.get("text").asText()).append(" ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing TIL content JSON: " + e.getMessage(), e);
        }

        return "다음 내용을 바탕으로 #의 갯수만큼 한국어로 질문과 답변을 작성합니다. 문제를 작성할때 문제내용은 간결하게. Format: Q: [Question] A: [Answer] " + promptBuilder.toString().trim();
    }

    private List<Question> parseQuestions(String responseContent, Til til, User_Information user) {
        List<Question> questions = new ArrayList<>();
        String[] qaPairs = responseContent.split("Q: ");
        log.info(responseContent);

        for (String qaPair : qaPairs) {
            if (!qaPair.trim().isEmpty()) {
                String[] qa = qaPair.split("A: ");
                if (qa.length == 2) {
                    String questionContent = qa[0].trim();
                    String answerContent = qa[1].trim();
                    questions.add(new Question(questionContent, answerContent, til, user));
                }
            }
        }

        return questions;
    }
}
