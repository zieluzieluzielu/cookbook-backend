package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Mail;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @org.junit.Test
    public void shouldSendEmailTest() {
        //Given
        Mail mail = new Mail("mac.ziel@gmail.com", "Test", "Test message", "test@cc.com");
        MimeMessagePreparator mimeMessagePreparator = simpleEmailService.createMimeMessage(mail, EmailType.NEW_RECIPE);
        //When
        simpleEmailService.send(mail, EmailType.NEW_RECIPE);

        //Then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
    }

    @org.junit.Test
    public void shouldSendEmailWithoutCc() {
        //Given
        Mail mail = new Mail("test@test.com", "test2@test.com", "test", "this is test");
        MimeMessagePreparator mimeMessagePreparator = simpleEmailService.createMimeMessage(mail, EmailType.NEW_RECIPE);
        //When
        simpleEmailService.send(mail, EmailType.NEW_RECIPE);

        //Then
        verify(javaMailSender, times(1)).send(refEq(mimeMessagePreparator));
    }
}