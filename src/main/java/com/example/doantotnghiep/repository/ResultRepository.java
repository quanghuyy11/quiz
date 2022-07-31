package com.example.doantotnghiep.repository;

import com.example.doantotnghiep.dto.ResultSubject;
import com.example.doantotnghiep.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    @Query(value = "SELECT * FROM result ORDER BY totalCorrect DESC limit 5", nativeQuery = true)
    List<Result> findTopFive();

    @Query(value = "SELECT * FROM result WHERE id_user = :id ", nativeQuery = true)
    List<Result> findByUsers(@Param("id") int id);

    @Query(value = "SELECT * FROM doantn.result WHERE id_exam= :idExam ORDER BY totalCorrect DESC ", nativeQuery = true)
    List<Result> findTopByExam(@Param("idExam") int idExam);

    @Query(value = "SELECT * FROM result WHERE id_exam = :id ", nativeQuery = true)
    List<Result> finResultByIdExams(@Param("id") int id);

    @Query(value = "SELECT * FROM result WHERE id_exam = :id ", nativeQuery = true)
    Result findByExams(@Param("id") int id);

    @Query(value = "SELECT * FROM result WHERE id_user = :id_user and id_exam = :id_exam ", nativeQuery = true)
    Result findByUsersAndExams(@Param("id_user")int id_user, @Param("id_exam") int id_exam);

    @Query(value = "select new com.example.doantotnghiep.dto.ResultSubject(s.name, avg(r.score), s.id)\n" +
            "from Subject s inner join Exam e on s.id = e.subject.id\n" +
            "inner join Result r on e.id = r.exams.id\n" +
            "where r.users.id = :id group by s.name")
    List<ResultSubject> findAvg(@Param("id") int id);

    @Query(value = "select new com.example.doantotnghiep.dto.ResultSubject(e.nameExam, r.score)\n" +
            "from Result r inner join Exam e on r.exams.id = e.id\n" +
            "inner join Subject s on e.subject.id = s.id where r.users.id = :idUser and s.id = :id")
    List<ResultSubject> findBySubject(@Param("idUser") int idUser, @Param("id") int id);
}
