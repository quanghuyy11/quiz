package com.example.doantotnghiep.repository;

import com.example.doantotnghiep.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question INNER JOIN exam_question ON question.quesId = exam_question.ques_Id WHERE exam_question.exam_Id = :id ",nativeQuery = true)
    List<Question> findAllId(@Param("id") int i);

    @Query(value = "SELECT * FROM question WHERE id_subject = :id ",nativeQuery = true)
    Page<Question> findAllBySubject(@Param("id") Optional<Integer> id, Pageable pageable);

    @Query(value = "SELECT * FROM question INNER JOIN exam_question ON question.quesId = exam_question.ques_Id \n" +
            "WHERE exam_id = :id",nativeQuery = true)
    List<Question> findAllByExams(@Param("id") int id);

    Page<Question> findAllByTitleContaining(String title, Pageable pageable);

}
