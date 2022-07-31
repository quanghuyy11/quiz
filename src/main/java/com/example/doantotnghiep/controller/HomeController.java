package com.example.doantotnghiep.controller;

import com.example.doantotnghiep.model.*;
import com.example.doantotnghiep.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    ResultService rService;
    @Autowired
    SubjectService subjectService;

    @GetMapping("/")
    public String showLoginn(){
        return "redirect:/default";
    }

    @GetMapping("/role")
    public String redirectRole(HttpSession session){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetail){
            MyUserDetail userDetail = (MyUserDetail)principal;
            for (GrantedAuthority role : userDetail.getAuthorities()){
                if (role.getAuthority().equals("ROLE_ADMIN")){
                    String userName = ((MyUserDetail) principal).getUsername();
                    session.setAttribute("userName",userName);
                    return "redirect:/user/list";
                }
            }
            String userName = ((MyUserDetail) principal).getUsername();
            session.setAttribute("userName",userName);
        }
        return "redirect:/default";
    }
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/default")
    public String show(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            String userName = ((UserDetails) principal).getUsername();
            User user = userService.findByUsername(userName);
            model.addAttribute("user",user);
        }
        Iterable<Subject> subjects = subjectService.findAll();
        int total = userService.findByTotalUser();
        String newUser = userService.findByNewUser();
        model.addAttribute("total", total-1);
        model.addAttribute("newUser",newUser);
        model.addAttribute("subjects",subjects);
        return "default-page";
    }

    @GetMapping("/error")
    public String error(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            String userName = ((UserDetails) principal).getUsername();
            model.addAttribute("userName",userName);
        }
        return "error";
    }


    @GetMapping("/aboutus")
    public String aboutUS(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails){
            String userName = ((UserDetails) principal).getUsername();
            model.addAttribute("userName",userName);
        }
        return "aboutUs";
    }
}
