package com.example.qc.controller;

import com.example.qc.QuizApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class IntroController {
    @FXML
    private TextField nameField;

    @FXML
    public void startQuiz() {
        String username = nameField.getText();
        if (!username.trim().isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(QuizApplication.class.getResource("/com/example/qc/quiz-view.fxml"));
                Stage stage = (Stage) nameField.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);

                QuizController quizController = loader.getController();
                quizController.setUsername(username);
                quizController.startQuiz();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
