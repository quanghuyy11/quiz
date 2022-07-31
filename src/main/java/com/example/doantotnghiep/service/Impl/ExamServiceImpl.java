package com.example.doantotnghiep.service.Impl;

import com.example.doantotnghiep.model.Exam;
import com.example.doantotnghiep.model.Result;
import com.example.doantotnghiep.repository.ExamRepository;
import com.example.doantotnghiep.repository.ResultRepository;
import com.example.doantotnghiep.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public void save(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void delete(Exam exam) {
        examRepository.delete(exam);
    }
    
    @Override
    public Page<Exam> findAll(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    @Override
    public Iterable<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam findById(int id) {
        return examRepository.findById(id).orElse(null);
    }

    @Override
    public List<Exam> findBySubject(int id){
        return examRepository.findBySubject(id);
    }

    @Override
    public Page<Exam> findBySubject(int id, Pageable pageable) {
        return examRepository.findBySubject(id, pageable);
    }

    @Override
    public Page<Exam> findAllByIdNameContaining(String name, Pageable pageable) {
        return examRepository.findAllByIdNameContaining(name, pageable);
    }

    @Override
    public List<Exam> checkExam(int id_user){
        List<Result> results = resultRepository.findByUsers(id_user);
        List<Exam> exams = new ArrayList<>();
        for (Result result : results){
            if (result.getTimeEnd() == null){
                Exam exam = examRepository.findByResults(result.getExams().getId());
                exams.add(exam);
            }
        }
        return exams;
    }

    @Override
    public Date convertTime(LocalDateTime localDateTime){
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Date timeQuiz = Date.from(zdt.toInstant());
        return timeQuiz;
    }
}

