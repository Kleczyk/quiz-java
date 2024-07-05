package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.model.QuestionDTO;
import com.example.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/questions")
    public List<QuestionDTO> getQuestions() {
        // Mapujemy Question na QuestionDTO
        return quizService.getQuestions().stream()
                .map(question -> {
                    QuestionDTO dto = new QuestionDTO();
                    dto.setQuestion(question.getQuestion());
                    dto.setOptions(question.getOptions());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/submit")
    public int submitAnswers(@RequestBody Map<String, String> answers, @RequestParam String username) {
        int score = quizService.checkAnswers(answers);
        try {
            quizService.saveResults(username, score);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }
}
