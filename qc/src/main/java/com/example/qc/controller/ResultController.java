package com.example.qc.controller;

import com.example.qc.QuizApplication;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.IOException;

public class ResultController {
    @FXML
    private Label scoreLabel;

    private int score;

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Twój wynik to: " + score);
        animateScore();
    }

    private void animateScore() {
        // Animacje dla wyniku
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), scoreLabel);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000), scoreLabel);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    @FXML
    public void restartQuiz() {
        try {
            FXMLLoader loader = new FXMLLoader(QuizApplication.class.getResource("/com/example/qc/intro-view.fxml"));
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
