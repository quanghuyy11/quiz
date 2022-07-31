package com.example.doantotnghiep.service.Impl;

import com.example.doantotnghiep.dto.ResultSubject;
import com.example.doantotnghiep.model.Exam;
import com.example.doantotnghiep.model.Result;
import com.example.doantotnghiep.model.User;
import com.example.doantotnghiep.repository.ResultRepository;
import com.example.doantotnghiep.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    ResultRepository resultRepository;

    @Override
    public void save(Result result) {
        resultRepository.save(result);
    }

    @Override
    public void delete(Result result){ resultRepository.delete(result);}

    @Override
    public List<Result> getTopFive() {
        return resultRepository.findTopFive();
    }

    @Override
    public List<Result> findByUsers(int id) {
        return resultRepository.findByUsers(id);
    }

    @Override
    public List<Result> findResultByIdExams(int id) {
        return resultRepository.finResultByIdExams(id);
    }

    @Override
    public Result findResultByIdExam(int id) {
        return resultRepository.findByExams(id);
    }

    @Override
    public Result findByUsersAndExams(int id_user, int id_exam){
        return resultRepository.findByUsersAndExams(id_user, id_exam);
    }

    @Override
    public void saveStartQuiz(String username, Timestamp timeStart, User user, Exam exam){
        Result result = new Result();
        result.setUsername(username);
        result.setUsers(user);
        result.setExams(exam);
        result.setTimeStart(timeStart);
        resultRepository.save(result);
    }

    @Override
    public List<ResultSubject> findAvg(int id) {
        List<ResultSubject> resultSubjects = resultRepository.findAvg(id);
        for (ResultSubject rs : resultSubjects){
            double temp1 = rs.getScore() * 1.0;
            DecimalFormat f = new DecimalFormat("##.00");
            rs.setScore(Double.parseDouble(f.format(temp1)));
        }
        return resultSubjects;
    }

    @Override
    public List<ResultSubject> findBySubject(int idUser, int id){
        return resultRepository.findBySubject(idUser, id);
    }
}
