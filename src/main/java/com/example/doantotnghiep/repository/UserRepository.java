package com.example.doantotnghiep.repository;

import com.example.doantotnghiep.dto.UserResult;
import com.example.doantotnghiep.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {
    @Query(value = "SELECT count(id) FROM user;",nativeQuery = true)
    Integer findByTotalUser();

    @Query(value = "SELECT id FROM user ORDER BY id DESC LIMIT 1;",nativeQuery = true)
    String findByNewUser();

    @Query(value = "SELECT * FROM user WHERE username = :username",nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE id = :id",nativeQuery = true)
    User findUserById(int id);

    @Query(value = "SELECT * FROM user WHERE username = :username",nativeQuery = true)
    Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT passWord\n" +
            "FROM user\n" +
            "WHERE username = :id",nativeQuery = true)
    String findByPass(@Param("id") String id);

    Page<User> findByRoles_Id(int id, Pageable pageable);

    List<User> findByRoles_Id(int id);

    @Query(value = "select new com.example.doantotnghiep.dto.UserResult(u.id, u.fullName, r.score, r.error, r.timeStart, r.timeEnd)" +
            "from User u inner join Result r on u.id = r.users.id where r.exams.id = :id")
    List<UserResult> findUsersByExamId(@Param("id") int id);
}
