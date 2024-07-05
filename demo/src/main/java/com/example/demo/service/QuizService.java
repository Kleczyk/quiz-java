package com.example.demo.service;

import com.example.demo.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

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
}
