package com.example.doantotnghiep.service;

import com.example.doantotnghiep.dto.UserResult;
import com.example.doantotnghiep.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void save(User user);

    void delete(User user);

    Page<User> findAll(Pageable pageable);

    Iterable<User> findAll();

    Page<User> findAllByRoles(int id, Pageable pageable);

    List<User> findAllByRoles(int id);

    User findByUsername(String username);

    User findUserById(int id);

//    Page<User> findAllByRoles(Pageable pageable);

    Integer findByTotalUser();

    String findByNewUser();

    boolean userExists(String temp);

    String findByPass(String username);

    List<UserResult> findUsersByExamId(int id);
}
