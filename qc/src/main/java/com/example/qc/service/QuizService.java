package com.example.qc.service;

import com.example.qc.model.QuestionDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class QuizService {
    private static final String BASE_URL = "http://localhost:8080/api";
    private Gson gson = new Gson();

    public List<QuestionDTO> getQuestions() throws IOException {
        URL url = new URL(BASE_URL + "/questions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        Type questionListType = new TypeToken<List<QuestionDTO>>() {}.getType();
        List<QuestionDTO> questions = gson.fromJson(reader, questionListType);
        reader.close();
        connection.disconnect();
        return questions;
    }

    public int submitAnswers(Map<String, String> answers, String username) throws IOException {
        URL url = new URL(BASE_URL + "/submit?username=" + username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        gson.toJson(answers, writer);
        writer.flush();
        writer.close();

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        int score = gson.fromJson(reader, Integer.class);  // Oczekujemy liczby zamiast obiektu JSON
        reader.close();
        connection.disconnect();
        return score;
    }
}
