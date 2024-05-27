package com.project.Retil.userAccount.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public String sendSimpleMessage(String to) {
        String token = createKey(); // 토큰 생성
        try {
            MimeMessage message = createMessage(to, token); // 메일 생성
            javaMailSender.send(message); // 메일 발송
        } catch (MessagingException e) {
            log.error("이메일 전송 실패"); // 에러 로그 기록
            throw new RuntimeException("이메일 전송 실패"); // 런타임 예외 발생
        }
        return token; // 생성된 토큰 반환
    }

    private String createKey() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999)); // 6자리로 포맷팅
    }

    private MimeMessage createMessage(String to, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
        message.setSubject("당신의 비밀번호 초기화 인증 번호입니다");
        message.setText("Code : " + token, "utf-8", "html");
        try {
            message.setFrom(new InternetAddress(fromEmail, "Admin"));
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to set sender's address", e);
            throw new RuntimeException("Failed to set sender's address", e);
        }
        return message;
    }
}