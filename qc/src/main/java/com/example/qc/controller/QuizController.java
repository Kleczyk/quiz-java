package com.example.qc.controller;

import com.example.qc.QuizApplication;
import com.example.qc.model.QuestionDTO;
import com.example.qc.service.QuizService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizController {
    @FXML
    private VBox quizContainer;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private Button submitAnswersButton;

    private QuizService quizService = new QuizService();
    private List<QuestionDTO> questions;
    private int currentQuestionIndex = 0;
    private Map<String, ToggleGroup> questionGroups = new HashMap<>();
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public void startQuiz() {
        try {
            questions = quizService.getQuestions();
            quizContainer.getChildren().clear();
            questionGroups.clear();
            currentQuestionIndex = 0;

            for (QuestionDTO question : questions) {
                ToggleGroup group = new ToggleGroup();
                questionGroups.put(question.getQuestion(), group);
            }

            showQuestion(currentQuestionIndex);
            nextQuestionButton.setDisable(false);
            submitAnswersButton.setDisable(true);
            progressBar.setProgress(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showQuestion(int index) {
        quizContainer.getChildren().clear();
        if (index >= 0 && index < questions.size()) {
            QuestionDTO question = questions.get(index);
            Label questionLabel = new Label(question.getQuestion());
            questionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            quizContainer.getChildren().add(questionLabel);

            ToggleGroup group = questionGroups.get(question.getQuestion());

            for (String option : question.getOptions()) {
                RadioButton radioButton = new RadioButton(option);
                radioButton.setToggleGroup(group);
                radioButton.setStyle("-fx-font-size: 14px;");
                quizContainer.getChildren().add(radioButton);
            }
        }

        double progress = (double) (index + 1) / questions.size();
        progressBar.setProgress(progress);

        if (index == questions.size() - 1) {
            nextQuestionButton.setDisable(true);
            submitAnswersButton.setDisable(false);
        } else {
            nextQuestionButton.setDisable(false);
            submitAnswersButton.setDisable(true);
        }
    }

    @FXML
    public void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            showQuestion(currentQuestionIndex);
        }
    }

    @FXML
    public void submitAnswers() {
        Map<String, String> answers = new HashMap<>();

        for (Map.Entry<String, ToggleGroup> entry : questionGroups.entrySet()) {
            Toggle selectedToggle = entry.getValue().getSelectedToggle();
            if (selectedToggle != null) {
                RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                answers.put(entry.getKey(), selectedRadioButton.getText());
            }
        }

        try {
            int score = quizService.submitAnswers(answers, username);
            showResult(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showResult(int score) {
        try {
            FXMLLoader loader = new FXMLLoader(QuizApplication.class.getResource("/com/example/qc/result-view.fxml"));
            Stage stage = (Stage) quizContainer.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            ResultController resultController = loader.getController();
            resultController.setScore(score);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
