package com.mzielinski.cookbook.scheduler;

import com.mzielinski.cookbook.config.AdminConfig;
import com.mzielinski.cookbook.domain.Mail;
import com.mzielinski.cookbook.repository.RecipeRepository;
import com.mzielinski.cookbook.service.EmailType;
import com.mzielinski.cookbook.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Recipes: weekly status";

    @Scheduled(cron = "0 10 * * * SUN")
    private void sendInformationEmail() {
        long size = recipeRepository.count();
        emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT,
                "Currently in your CookBook you have: " + size + (size == 1 ? " recipe" : " recipes")), EmailType.SCHEDULED_MAIL);
    }

}
