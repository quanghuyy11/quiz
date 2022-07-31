package com.example.doantotnghiep.controller;

import com.example.doantotnghiep.model.Exam;
import com.example.doantotnghiep.model.Subject;
import com.example.doantotnghiep.service.ExamService;
import com.example.doantotnghiep.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @Autowired
    ExamService examService;

    @GetMapping("/subject/list")
    public String showList(@RequestParam("keyword") Optional<String> nameSubject,
                           Model model, @PageableDefault(value = 5) Pageable pageable){
        Page<Subject> subjects;
        if (nameSubject.isPresent()){
            subjects = subjectService.findAllByNameSubjectContaining(nameSubject.get(), pageable);
            model.addAttribute("subjects", subjects);
            model.addAttribute("keyword", nameSubject.get());
            return "subject/listSubject";
        }
        subjects = subjectService.findAll(pageable);
        model.addAttribute("subjects", subjects);
        return "subject/listSubject";
    }

    @GetMapping("/subject/create")
    public String create(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject/createSubject";
    }

    @PostMapping("/subject/create")
    public String save(@Valid @ModelAttribute Subject subject, BindingResult bindingResult,
                       RedirectAttributes ra) {
        if (subjectService.subjectExists(subject.getName())) {
            bindingResult.addError(new FieldError("subject","name","Môn này có rồi"));
            return "subject/createSubject";
        }
        subjectService.save(subject);
        ra.addFlashAttribute("message","Thêm thành công");
        return "redirect:/subject/list";
    }

    @GetMapping("/subject/editSubject/{id}")
    public String editSubject(@PathVariable("id") Integer id, Model model)  {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        return "subject/editSubject";
    }

    @PostMapping("/subject/update")
    public String update(@Valid Subject subject, BindingResult bindingResult, RedirectAttributes ra){
        if (bindingResult.hasFieldErrors()) {
            return "subject/editSubject";
        }
        subjectService.save(subject);
        ra.addFlashAttribute("message","sửa thành công");
        return "redirect:/subject/list";
    }

    @GetMapping("/subject/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes re) {
        Subject subject = subjectService.findById(id);

        List<Exam> exams = examService.findBySubject(id);
        if(exams.size() != 0){
            re.addFlashAttribute("message", "Không được xóa môn học đã có đề thi");
            return "redirect:/subject/list";
        }else {
            subjectService.delete(subject);
            return "redirect:/subject/list";
        }
    }
}
