package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return quizService.getQuestions();
    }

    @PostMapping("/submit")
    public Map<String, Object> submitAnswers(@RequestBody Map<String, String> answers) {
        List<Question> questions = quizService.getQuestions();
        int score = 0;
        for (Question question : questions) {
            if (question.getAnswer().equals(answers.get(question.getQuestion()))) {
                score++;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        return result;
    }
}
