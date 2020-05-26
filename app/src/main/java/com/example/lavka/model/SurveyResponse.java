package com.example.lavka.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SurveyResponse {

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("questionAnswers")
    @Expose
    private Map<String, List<String>> questionAnswers;

    public SurveyResponse(Long userId, Map<String, List<String>> questionAnswers) {
        this.userId = userId;
        this.questionAnswers = questionAnswers;
    }
}
