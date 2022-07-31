package com.example.doantotnghiep.controller;

import com.example.doantotnghiep.model.Exam;
import com.example.doantotnghiep.model.Question;
import com.example.doantotnghiep.model.Subject;
import com.example.doantotnghiep.service.ExamService;
import com.example.doantotnghiep.service.QuestionService;
import com.example.doantotnghiep.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;


@Controller
public class QuestionController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamService examService;

    @ModelAttribute("subjects")
    public Iterable<Subject> subjects() {
        return subjectService.findAll();
    }

    @ModelAttribute("exams")
    public Iterable<Exam> exams(){
        return examService.findAll();
    }

    @GetMapping("/question/list")
    public String showList(@RequestParam("keyword") Optional<String> name,
                           Model model, @PageableDefault(value = 5) Pageable pageable){
        Page<Question> questions;
        if (name.isPresent()){
            questions = questionService.findAllByTitleContaining(name.get(), pageable);
            model.addAttribute("questions", questions);
            model.addAttribute("keyword", name.get());
            return "question/listQuestion";
        }
        questions = questionService.findAll(pageable);
        model.addAttribute("questions", questions);

        return "question/listQuestion";
    }

    @GetMapping("/question/create")
    public String create(Model model) {
//        ModelAndView modelAndView = new ModelAndView("question/createQuestion");
        model.addAttribute("question", new Question());
        model.addAttribute("subjects", subjectService.findAll());
        return "question/createQuestion";
    }

    @PostMapping("/question/create")
    public String save(@Valid @ModelAttribute Question question, BindingResult bindingResult, Model model, RedirectAttributes ra) {
//        if (questionService.existById(id) {
//            bindingResult.addError(new FieldError("question", "id", "Cau hoi đã tồn tại"));
//        }

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("subjects", subjectService.findAll());
            return "question/createQuestion";
        }
        if (question.getOptionE() == ""){
            question.setOptionE(null);
        }
        if (question.getOptionF() == ""){
            question.setOptionF(null);
        }
        if (question.getAns1() == "" && question.getAns2() != "" || question.getAns2() == "" && question.getAns1() != ""){
            question.setAnswerNumb(2);
        }
        if (question.getAns2() == "" && question.getAns1() == ""){
            question.setAnswerNumb(1);
        }
        if (question.getAns2() != "" && question.getAns1() != ""){
            question.setAnswerNumb(3);
        }
        if (question.getAns1() == ""){
            question.setAns1(null);
        }
        if (question.getAns2() == ""){
            question.setAns2(null);
        }

        questionService.save(question);
        ra.addFlashAttribute("message","Thêm thành công");
        return "redirect:/question/list";
    }

    @GetMapping("/question/import")
    public String getFile() {
        return "question/importFile";
    }

    @PostMapping("question/import")
    public String importFile(@RequestParam("file") MultipartFile file, RedirectAttributes ra) throws IOException {
        questionService.importFile(file);
        ra.addFlashAttribute("message", "Thêm thành công");
        return "redirect:/question/list";
    }

    @GetMapping("/question/editQuest/{id}")
    public String editMember(@PathVariable("id") Integer id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "question/editQuestion";
    }

    @PostMapping("/question/update")
    public String update(@Valid Question question, BindingResult bindingResult, RedirectAttributes ra){
        if (bindingResult.hasFieldErrors()) {
            return "question/editQuestion";
        }
        questionService.save(question);
        ra.addFlashAttribute("message","sửa thành công");
        return "redirect:/question/list";
    }

    @GetMapping("/question/delete/{id}")
    public String delete(@PathVariable("id") Integer id,RedirectAttributes ra){
        Question question = questionService.findById(id);
        questionService.delete(question);
        ra.addFlashAttribute("message","Xóa thành công");
        return "redirect:/question/list";
    }

}

