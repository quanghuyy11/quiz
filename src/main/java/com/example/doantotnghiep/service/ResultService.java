package com.example.doantotnghiep.service;

import com.example.doantotnghiep.dto.ResultSubject;
import com.example.doantotnghiep.model.Exam;
import com.example.doantotnghiep.model.Result;
import com.example.doantotnghiep.model.User;

import java.sql.Timestamp;
import java.util.List;


public interface ResultService {
    void save(Result result);

    void delete(Result result);

    List<Result> getTopFive();

    List<Result> findByUsers(int id);

    List<ResultSubject> findAvg(int id);

    List<Result> findResultByIdExams(int id);

    Result findResultByIdExam(int id);

    Result findByUsersAndExams(int id_user, int id_exam);

    void saveStartQuiz(String username, Timestamp timeStart, User user, Exam exam);

    List<ResultSubject> findBySubject(int idUser, int id);
}
