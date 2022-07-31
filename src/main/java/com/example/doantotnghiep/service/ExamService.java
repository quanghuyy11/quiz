package com.example.doantotnghiep.service;


import com.example.doantotnghiep.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface ExamService {
    Page<Exam> findAll(Pageable pageable);

    Iterable<Exam> findAll();

    Exam findById(int id);

    void save(Exam exam);

    void delete(Exam exam);

    List<Exam> findBySubject(int id);

    Page<Exam> findBySubject(int id, Pageable pageable);

    Page<Exam> findAllByIdNameContaining(String name, Pageable pageable);

    List<Exam> checkExam(int id);

    Date convertTime(LocalDateTime localDateTime);
}
