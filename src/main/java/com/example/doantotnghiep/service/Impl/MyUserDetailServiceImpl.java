package com.example.doantotnghiep.service.Impl;

import com.example.doantotnghiep.model.MyUserDetail;
import com.example.doantotnghiep.model.User;
import com.example.doantotnghiep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("user name not found" + username);
        }
        return new MyUserDetail(user);
    }
}
