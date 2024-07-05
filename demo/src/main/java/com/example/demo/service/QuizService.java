package com.example.demo.service;

import com.example.demo.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {
    private List<Question> questions;
    private Gson gson = new Gson();

    @PostConstruct
    public void loadQuestions() throws Exception {
        ClassPathResource resource = new ClassPathResource("questions.json");
        Reader reader = new InputStreamReader(resource.getInputStream());
        Type questionListType = new TypeToken<List<Question>>() {}.getType();
        questions = gson.fromJson(reader, questionListType);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int checkAnswers(Map<String, String> answers) {
        int score = 0;
        for (Question question : questions) {
            if (question.getAnswer().equals(answers.get(question.getQuestion()))) {
                score++;
            }
        }
        return score;
    }

    public void saveResults(String username, int score) throws IOException {
        String resultFileName = "src/main/resources/results.json";
        File resultFile = new File(resultFileName);
        Map<String, Integer> results;

        if (resultFile.exists()) {
            try (Reader reader = new FileReader(resultFile)) {
                Type type = new TypeToken<Map<String, Integer>>() {}.getType();
                results = gson.fromJson(reader, type);
            }
        } else {
            results = new HashMap<>();
        }

        results.put(username, score);

        try (Writer writer = new FileWriter(resultFile)) {
            gson.toJson(results, writer);
        }
    }
}
