package com.example.doantotnghiep.service;

import com.example.doantotnghiep.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {
    void save(Subject subject);

    void delete(Subject subject);

    Subject findById(int id);

    Iterable<Subject> findAll();

    Page<Subject> findAll(Pageable pageable);

    Page<Subject> findAllByNameSubjectContaining(String name, Pageable pageable);

    boolean subjectExists(String name);
}
