package com.eparking.model.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        System.out.println("========== EMAIL (not actually sent) ==========");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("------------------------------------------------");
        System.out.println(htmlBody);
        System.out.println("================================================");
    }
}