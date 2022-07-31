package com.example.doantotnghiep.repository;

import com.example.doantotnghiep.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
   @Query(value = "SELECT e FROM Exam e WHERE e.subject.id = :id")
   Page<Exam> findBySubject(@Param("id") int id, Pageable pageable);

   Page<Exam> findAllByIdNameContaining(String name, Pageable pageable);

   @Query(value = "SELECT * FROM exam WHERE subject_id = :id",
           nativeQuery = true)
   List<Exam> findBySubject(int id);

   @Query(value = "select exam.* from exam inner join result on exam.id = result.id_exam where result.id_exam = :id",
           nativeQuery = true)
   Exam findByResults(@Param("id") int id);
}
