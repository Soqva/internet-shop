package com.s0qva.application.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @SneakyThrows
    public void sendEmail(String recipientEmail, String subject, String text, String attachmentName, String pathToAttachment) {
        var message = mailSender.createMimeMessage();
        var file = new FileSystemResource(new File(pathToAttachment));
        var helper = new MimeMessageHelper(message, true);

        helper.setFrom("s0qva69@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(text);
        helper.addAttachment(attachmentName, file);

        mailSender.send(message);
    }
}
