package com.example.doantotnghiep.service.Impl;

import com.example.doantotnghiep.dto.UserResult;
import com.example.doantotnghiep.model.User;
import com.example.doantotnghiep.repository.UserRepository;
import com.example.doantotnghiep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllByRoles(int id, Pageable pageable) {
        return userRepository.findByRoles_Id(id, pageable);
    }

    @Override
    public List<User> findAllByRoles(int id){
        return userRepository.findByRoles_Id(id);
    }
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
    @Override
    public Integer findByTotalUser(){return userRepository.findByTotalUser();}

    @Override
    public String findByNewUser(){return userRepository.findByNewUser();}

    @Override
    public boolean userExists(String temp){
        return userRepository.findUserByUsername(temp).isPresent();
    }

    @Override
    public String findByPass(String username) {
        return userRepository.findByPass(username);
    }

    @Override
    public List<UserResult> findUsersByExamId(int id){return userRepository.findUsersByExamId(id); }

}
