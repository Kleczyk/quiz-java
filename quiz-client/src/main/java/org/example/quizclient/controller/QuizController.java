package org.example.quizclient.controller;

import org.example.quizclient.model.Question;
import org.example.quizclient.service.QuizService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizController {
    @FXML
    private TextField nameField;
    @FXML
    private VBox quizContainer;
    @FXML
    private Label scoreLabel;

    private QuizService quizService = new QuizService();
    private List<Question> questions;
    private Map<String, ToggleGroup> questionGroups = new HashMap<>();

    @FXML
    public void startQuiz() {
        try {
            questions = quizService.getQuestions();
            quizContainer.getChildren().clear();

            for (Question question : questions) {
                Label questionLabel = new Label(question.getQuestion());
                quizContainer.getChildren().add(questionLabel);

                ToggleGroup group = new ToggleGroup();
                questionGroups.put(question.getQuestion(), group);

                for (String option : question.getOptions()) {
                    RadioButton radioButton = new RadioButton(option);
                    radioButton.setToggleGroup(group);
                    quizContainer.getChildren().add(radioButton);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            int score = quizService.submitAnswers(answers);
            scoreLabel.setText("Your score is: " + score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
