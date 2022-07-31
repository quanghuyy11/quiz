package com.example.doantotnghiep.controller;

import com.example.doantotnghiep.model.*;
import com.example.doantotnghiep.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class QuizController {
    private boolean status = true;
    private Date timer = null;
    Boolean submitted = false;

    @Autowired
    ResultService resultService;

    @Autowired
    QuizService qService;

    @Autowired
    UserService userService;

    @Autowired
    ResultService rService;

    @Autowired
    ExamService examService;

    @GetMapping("/total")
    public String home1(Model model) {
        List<Result> sList = qService.getTopScore();
        model.addAttribute("sList", sList);
        int total = userService.findByTotalUser();
        model.addAttribute("total", total);
        return "";
    }

    @GetMapping("/quiz/{idUser}/{id}")
    public String beforeQuiz(@PathVariable("idUser") int idUser,@PathVariable("id") int id){
        this.status = true;
        return "redirect:/quiz1/" + idUser + "/" + id;
    }

    @GetMapping("/quiz1/{idUser}/{id}")
    public String quiz(@PathVariable("idUser") int idUser, @PathVariable("id") int id,
                       Model m, HttpSession session) throws ParseException {

        submitted = false;
        Exam exam = examService.findById(id);
        User user1 = userService.findUserById(idUser);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int time = exam.getTime();
        if (this.status) {
            timer = new Date(System.currentTimeMillis());
            timer.setMinutes(timer.getMinutes() + time);
            this.status = false;
        }

        //save time start
        Date now = new Date();
        Timestamp ts = new Timestamp(now.getTime());
        resultService.saveStartQuiz(user1.getFullName(), ts, user1, exam);

        QuestionForm qForm = qService.getQuestions(id);
        qForm.setError(0);
        List<Result> sList = qService.getTopScore();
        int total = userService.findByTotalUser();
        m.addAttribute("qForm", qForm);
        m.addAttribute("sList", sList);
        m.addAttribute("total", total);
        m.addAttribute("idExam", id);
        m.addAttribute("idUser", idUser);
        m.addAttribute("futureDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatter.format(timer)));
        return "exam/quizTest";

    }

    @PostMapping("/submit1/{idUser}/{id}")
    public String submit1 (@PathVariable(name = "idUser") int idUser, @PathVariable(name = "id") int idExam,
                           @ModelAttribute QuestionForm qForm, Model m){
        this.status = true;
        if (!submitted) {
            Exam exam = examService.findById(idExam);
            Result result = resultService.findByUsersAndExams(idUser, idExam);
            result.setTotalCorrect(qService.getResult(qForm));

            //Convert score
            double temp = (qService.getResult(qForm)/(double)exam.getNumberQues())*10.0;
            DecimalFormat f = new DecimalFormat("##.00");
            double score = Double.parseDouble(f.format(temp));
            result.setScore(score);

            //get error
            result.setError(qForm.getError());

            //save time end
            Date now = new Date();
            Timestamp ts = new Timestamp(now.getTime());
            result.setTimeEnd(ts);
            qService.saveScore(result);

            m.addAttribute("result", result);
            m.addAttribute("exam", exam);
            m.addAttribute("qForm", qForm);
            m.addAttribute("idExam", idExam);
            m.addAttribute("no",1);
            submitted = true;
        }
        return "exam/resultTest";
    }

    @GetMapping("/score/{id}")
    public String score (@PathVariable(name = "id") int idExam,Model m){
        List<Result> sList = qService.getTopScoreByExam(idExam);
        m.addAttribute("sList", sList);
        return "exam/scoreboard";
    }

    @GetMapping("/honorthegoldboard")
    public String honorthegoldboard (Model m){
        int total = userService.findByTotalUser();
        m.addAttribute("total", total);
        return "Huy/HonorTheGoldBoard";
    }

}
