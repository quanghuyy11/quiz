package com.example.doantotnghiep.controller;

import com.example.doantotnghiep.dto.ResultSubject;
import com.example.doantotnghiep.model.Exam;
import com.example.doantotnghiep.model.Role;
import com.example.doantotnghiep.model.User;
import com.example.doantotnghiep.service.ExamService;
import com.example.doantotnghiep.service.ResultService;
import com.example.doantotnghiep.service.UserService;
import com.example.doantotnghiep.utils.AmazonUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MemberController {
    private final AmazonUpload amazonUpload;

    @Autowired
    MemberController(AmazonUpload amazonUpload) {
        this.amazonUpload = amazonUpload;
    }

    @Autowired
    private ExamService examService;

    @Autowired
    UserService userService;

    @Autowired
    ResultService resultService;

    @ModelAttribute("exams")
    public Iterable<Exam> showAll(){
        return examService.findAll();
    }

    @GetMapping(value = "/view/{id}")
    public String showMemberView(@PathVariable("id") int id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("users",user);
        return "info/view";
    }

    @GetMapping("/signup")
    public String showSignUp(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
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
            return "register";
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
        model.addAttribute("message","Đăng kí thành công !");
        userService.save(user);
        return "login";
    }

    @PostMapping("/edit")
    public String editView(@Valid @ModelAttribute("users") User user,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()){
            return "info/view";
        }else
            userService.save(user);
        model.addAttribute("message","Cập Nhật Thành Công !");
        model.addAttribute("users",user);
        return "info/view";
    }

    @GetMapping(value = "/editMember/{id}")
    public String showMemberEdit(@PathVariable("id") int id,Model model){
        User user = userService.findUserById(id);
        model.addAttribute("users",user);
        return "info/editMember";
    }

    @PostMapping("/editMember")
    public String editMember(@Valid @ModelAttribute("users") User users ,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam("file") MultipartFile photo){
        if (bindingResult.hasErrors()){
            model.addAttribute("userName",users.getId());
            return "info/editMember";
        }else {
            users.setImg(this.amazonUpload.uploadFile(photo));
            userService.save(users);
            model.addAttribute("message","Cập Nhật Thành Công !");
            model.addAttribute("userName",users);
            return "info/view";
        }
    }

    @GetMapping(value = "/editPass/{id}")
    public String showMemberEditPass(@PathVariable("id") int id,Model model){
        User users = userService.findUserById(id);
        model.addAttribute("users",users);
        return "info/editPass";
    }

    @PostMapping("/editPass")
    public String editPass(@Valid @ModelAttribute("users") User users , BindingResult bindingResult, Model model,
                           @RequestParam("oldPass") String oldPass, RedirectAttributes redirectAttributes){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPass1 = oldPass;
        String oldPass2 = userService.findByPass(users.getUsername());
        if (!passwordEncoder.matches(oldPass1, oldPass2)){
            model.addAttribute("messages","Mật Khẩu Không Đúng !");
            return "info/editPass";
        }
        if (users.getPassWord() != null && users.getRePassWord() != null){
            if (!users.getPassWord().equals(users.getRePassWord())){
                bindingResult.addError(new FieldError("users","rePassWord","Mật khẩu phải trùng nhau"));
                return "info/editPass";
            }
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("userName",users.getId());
            return "info/editPass";
        }else {
            users.setPassWord(passwordEncoder.encode(users.getPassWord()));
            users.setRePassWord(passwordEncoder.encode(users.getRePassWord()));
            userService.save(users);
            redirectAttributes.addAttribute("userName", users.getId());
            redirectAttributes.addAttribute("id", users.getId()).
                    addFlashAttribute("message","Cập nhật thành công ! ");
            return "redirect:/editMember/{id}";
        }
    }

    @GetMapping(value = "/history/{id}")
    public String showHistory(@PathVariable("id") int id, Model model){
        User user = userService.findUserById(id);
        List<ResultSubject> resultSubjects = resultService.findAvg(id);
        System.out.println(resultSubjects.get(0).getId());
        model.addAttribute("results", resultSubjects);
        model.addAttribute("users", user);
        return "info/history";
    }

    @GetMapping(value = "/scoreUser/{idUser}/{id}")
    public String showScore(@PathVariable("idUser") int idUser,
                            @PathVariable("id") int id, Model model){
        List<ResultSubject> resultSubjects = resultService.findBySubject(idUser, id);
        model.addAttribute("results", resultSubjects);
        model.addAttribute("idUser", idUser);
        return "info/score";
    }
}
