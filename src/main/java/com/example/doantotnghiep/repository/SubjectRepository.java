package com.example.doantotnghiep.repository;

import com.example.doantotnghiep.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Page<Subject> findAllByNameContaining(String name, Pageable pageable);

    Subject findById(int id);

    Optional<Subject> findByName(String name);
}
