package com.example.doantotnghiep.service;

import com.example.doantotnghiep.model.QuestionForm;
import com.example.doantotnghiep.model.Result;
import java.util.List;


public interface QuizService {
    QuestionForm getQuestions(int id);

    double getResult(QuestionForm qForm);

    void saveScore(Result result);

    List<Result> getTopScore();

    List<Result> getTopScoreByExam(int idExam);
}
