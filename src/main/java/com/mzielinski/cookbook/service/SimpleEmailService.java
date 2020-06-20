package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
@Service
public class SimpleEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(final Mail mail, EmailType emailType) {
        try {
            LOGGER.info("Starting email preparation..");
            javaMailSender.send(createMimeMessage(mail, emailType));
            if (emailType == EmailType.NEW_RECIPE) {
                LOGGER.info("Email about newly created recipe was sent successfully.");
            } else if (emailType == EmailType.SCHEDULED_MAIL) {
                LOGGER.info("Scheduled email reporting the current number of recipes was sent successfully.");
            }
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    public MimeMessagePreparator createMimeMessage(final Mail mail, EmailType emailType) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());

            if (emailType == EmailType.NEW_RECIPE) {
                messageHelper.setText(mailCreatorService.buildRecipeEmail(mail.getMessage()), true);
            } else if (emailType == EmailType.SCHEDULED_MAIL) {
                messageHelper.setText(mailCreatorService.recipesQuantityEmail(mail.getMessage()), true);
            }

            if (mail.getToCc() != null && !mail.getToCc().equals("")) {
                messageHelper.setCc(mail.getToCc());
            }


        };
    }
}
