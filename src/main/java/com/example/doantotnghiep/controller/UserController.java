package com.example.doantotnghiep.controller;

import com.example.doantotnghiep.model.*;
import com.example.doantotnghiep.service.ExamService;
import com.example.doantotnghiep.service.ResultService;
import com.example.doantotnghiep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ExamService examService;

    @Autowired
    ResultService resultService;

    @GetMapping("/user/list")
    public String showList(@RequestParam("keyword") Optional<Integer> id,
                           Model model, @PageableDefault(value = 5) Pageable pageable) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String userName = ((UserDetails) principal).getUsername();
            model.addAttribute("userName", userName);
        }
        if (id.isPresent()){
            User user = userService.findUserById(id.get());
            model.addAttribute("users", user);
            model.addAttribute("temp", id.get());
            return "user/list";
        }
        Page<User> users;
        users = userService.findAllByRoles(2, pageable);
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user/create";
    }

    @PostMapping("/user/create")
    public String saveUser(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, Model model) throws ParseException {
        if (userService.userExists(user.getUsername())){
            bindingResult.addError(new FieldError("user","username","Tài khoản đã tồn tại."));
        }
        if (userService.userExists(user.getEmail())){
            bindingResult.addError(new FieldError("user","email","Email đã tồn tại."));
        }
        if (user.getPassWord() != null && user.getRePassWord() != null){
            if (!user.getPassWord().equals(user.getRePassWord())){
                bindingResult.addError(new FieldError("user","rePassWord","Mật khẩu phải trùng nhau"));
            }
        }
        if (bindingResult.hasErrors()){
            return "user/create";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setRePassWord(user.getPassWord());

        //Create role for user
        Role role = new Role(2, "ROLE_KHACH");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        //send data
        model.addAttribute("message","Thêm thành công !");
        userService.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/editMember/{id}")
    public String editMember(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/editMember";
    }

    @PostMapping("/user/update")
    public String update(@Validated User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            return "user/editMember";
        } else {
            User user1 = userService.findUserById(user.getId());
            user1.setFullName(user.getFullName());
            user1.setEmail(user.getEmail());
            user1.setAddress(user.getAddress());
            user1.setPhoneNumber(user.getPhoneNumber());
            userService.save(user1);
            redirectAttributes.addFlashAttribute("message", "Cập Nhật Thành Công !");
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/editPass/{id}")
    public String editPass(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/editPass";
    }

    @PostMapping("/user/updatePass")
    public String updatePass(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model,
                             @RequestParam("oldPass") String oldPass,RedirectAttributes redirectAttributes) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPass1 = oldPass;
        String oldPass2 = userService.findByPass(user.getUsername());
        if (!passwordEncoder.matches(oldPass1, oldPass2)){
            model.addAttribute("messages","Mật Khẩu Không Đúng !");
            return "user/editPass";
        }
        if (user.getPassWord() != null && user.getRePassWord() != null) {
            if (!user.getPassWord().equals(user.getRePassWord())) {
                bindingResult.addError(new FieldError("user", "rePassWord", "Mật khẩu phải trùng nhau"));
                return "user/editPass";
            }
        }
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("userName",user.getId());
            return "user/editPass";
        }
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setRePassWord(passwordEncoder.encode(user.getRePassWord()));
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Cập Nhật Thành Công !");
        return "redirect:/user/list";
    }

    @GetMapping("/user/resetExam/{id}")
    public String listExamByUser(@PathVariable("id") int id, Model model) {
        List<Exam> exams = examService.checkExam(id);
        model.addAttribute("exams", exams);
        model.addAttribute("idUser", id);
        return "user/examByUser";
    }

    @GetMapping("/user/reset/{idUser}/{id}")
    public String resetExam(@PathVariable("idUser") int idUser,
                            @PathVariable("id") int id,
                            RedirectAttributes ra) {
        Result result = resultService.findByUsersAndExams(idUser, id);
        resultService.delete(result);
        ra.addFlashAttribute("message", "Đã reset bài thi");
        return "redirect:/user/list";
    }
}
