package com.example.doantotnghiep.service;

import com.example.doantotnghiep.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Page<Question> findAll(Pageable pageable);

    Iterable<Question> findAll();

    Question findById(Integer id);

    void save(Question question);

    void delete(Question question);

    Page<Question> findAllByTitleContaining(String title, Pageable pageable);

    Page<Question> findAllBySubject(Optional<Integer> id, Pageable pageable);

    List<Question> findAllByExams(int id);

    void importFile(MultipartFile file) throws IOException;
}
