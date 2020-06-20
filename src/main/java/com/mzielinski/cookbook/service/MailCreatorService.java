package com.mzielinski.cookbook.service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {


    public String buildRecipeEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        return message;
    }

    public String recipesQuantityEmail(String message) {
        return message;
    }
}
