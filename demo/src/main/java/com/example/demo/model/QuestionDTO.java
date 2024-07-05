package com.example.demo.model;

import java.util.List;

public class QuestionDTO {
    private String question;
    private List<String> options;

    // Gettery i settery
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
