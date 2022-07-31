package com.example.doantotnghiep.controller;

import com.example.doantotnghiep.dto.UserResult;
import com.example.doantotnghiep.model.*;
import com.example.doantotnghiep.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@SessionAttributes("question")
public class ExamController {
    @Autowired
    UserService userService;

    @Autowired
    ResultService resultService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private QuestionService questionService;

    @ModelAttribute("subjects")
    public Iterable<Subject> subjects() {
        return subjectService.findAll();
    }


    @ModelAttribute("questions")
    public Iterable<Question> questions() {
        return questionService.findAll();
    }

    @ModelAttribute("question")
    public HashMap<Long, Question> setExam() {
        return new HashMap<>();
    }

    @GetMapping("/exam/list")
    public String showList(@RequestParam("subjectId") Optional<Integer> subjectId,
                           @RequestParam("keyword") Optional<String> name,
                           Model model, @PageableDefault(value = 5) Pageable pageable) {
        Page<Exam> exams;
        model.addAttribute("subjects", subjectService.findAll());
        if (!name.isPresent()) {
            if (subjectId.isPresent()) {
                exams = examService.findBySubject(subjectId.get(), pageable);
                model.addAttribute("exams", exams);
                model.addAttribute("subjectId", subjectId.get());
                return "exam/listExam";
            }
            exams = examService.findAll(pageable);
        } else {
            exams = examService.findAllByIdNameContaining(name.get(), pageable);
            model.addAttribute("keyword", name.get());
        }
        model.addAttribute("exams", exams);
        return "exam/listExam";
    }

    @GetMapping("/exam/create")
    public String showCreateForm(@RequestParam(value = "subjectId", required = false) Optional<Integer> subjectId,
                                 @CookieValue(value = "setQuestion", defaultValue = "") String setQuestion,
                                 @PageableDefault(value = 100) Pageable pageable, Model model   ) {
        model.addAttribute("subjects", subjectService.findAll());
        if (subjectId.isPresent()) {
            Page<Question> questions1 = questionService.findAllBySubject(subjectId, pageable);
            model.addAttribute("question1", questions1);
            model.addAttribute("exam", new Exam());
            model.addAttribute("subjectId", subjectId.get());
            return "exam/createExam";
        }
        model.addAttribute("exam", new Exam());
        Cookie cookie = new Cookie("setQuestion", setQuestion);
        model.addAttribute("cookieValue", cookie);
        return "exam/createExam";
    }

    @PostMapping("/exam/create")
    public String saveExam(@Validated @ModelAttribute("exam") Exam exam,
                           BindingResult bindingResult, RedirectAttributes re) {
        if (bindingResult.hasFieldErrors()) {
            return "exam/createExam";
        } else {
            re.addFlashAttribute("message", "Tạo đề thi thành công!");
            examService.save(exam);
            return "redirect:/exam/list";
        }
    }

    @GetMapping("/exam/editExam/{id}")
    public String editMember(@PathVariable("id") Integer id, Model model) {
        Exam exam = examService.findById(id);
        model.addAttribute("exam", exam);
        return "exam/editExam";
    }

    @PostMapping("/exam/update")
    public String update(@Validated Exam exam, BindingResult bindingResult, RedirectAttributes re) {
        if (bindingResult.hasFieldErrors()) {
            return "exam/editExam";
        } else {
            re.addFlashAttribute("message", "chỉnh sửa đề thi thành công!");
            examService.save(exam);
            return "redirect:/exam/list";
        }
    }

    @GetMapping("/exam/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes re) {
        Exam exam = examService.findById(id);
//        examService.deletebyExam(id);
        List<Result> result = resultService.findResultByIdExams(id);
        if(result.size()!=0){
            re.addFlashAttribute("message", "Không được xoá đề thi đã thi rồi!");
            return "redirect:/exam/list";
        }else {
            examService.delete(exam);
            return "redirect:/exam/list";
        }
    }

    @GetMapping("/exam/score/{id}")
    public String score(@PathVariable("id") Integer id, Model model) {
        List<UserResult> info = userService.findUsersByExamId(id);
        model.addAttribute("users", info);
        return "exam/scoreUser";
    }


    @GetMapping("/exam/questionInExam/{id}")
    public String questionInExam(@PathVariable("id") Integer id, Model model) {
        List<Question> questions = questionService.findAllByExams(id);
        model.addAttribute("questions", questions);
        return "question/questionInExam";
    }

    @GetMapping("/listExamSubject/{id}")
    public String listExamSubject(@PathVariable int id, Model model,
                                  HttpSession session) {
        if(session.getAttribute("userName") != null){
            String userName = "null";
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
                model.addAttribute("userName", userName);
            }
            String newUser = userService.findByNewUser();
            User user = userService.findByUsername(userName);

            //get status from result
            List<Result> results = resultService.findByUsers(user.getId());

            //get exam from subject
            List<Exam> exams = examService.findBySubject(id);
            List<Integer> arrayNumber = new ArrayList<>();
            List<Integer> check = new ArrayList<>();
            int dem = 0;
            int num = 0;
            if (results != null) {
                for (Exam exam : exams) {
                    LocalDateTime ldt = exam.getTimeQuiz();
                    Date timeQuiz = examService.convertTime(ldt);
                    Date now = new Date();
                    if (now.getTime() < timeQuiz.getTime()){
                        num++;
                    }
                    for (Result r : results) {
                        if (exam.getId() == r.getExams().getId()) {
                            dem++;
                        }
                    }
                    check.add(num);
                    arrayNumber.add(dem);
                    num = 0;
                    dem = 0;
                }
            }
            List<Result> sList = resultService.getTopFive();
            model.addAttribute("sList", sList);
            int total = userService.findByTotalUser();
            model.addAttribute("user",user);
            model.addAttribute("total", total);
            model.addAttribute("newUser", newUser);
            model.addAttribute("exams", exams);
            model.addAttribute("check", check);
            model.addAttribute("arrayNumber", arrayNumber);
            model.addAttribute("id", id);
            return "listExamSubject";
        }else {
            return "redirect:/login";
        }
    }

}
