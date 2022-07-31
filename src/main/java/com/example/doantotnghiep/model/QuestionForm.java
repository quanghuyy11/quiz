package com.example.doantotnghiep.model;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class QuestionForm {
    private List<Question> questions;
    private int error;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
