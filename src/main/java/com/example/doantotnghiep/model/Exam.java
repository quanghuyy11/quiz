package com.example.doantotnghiep.model;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank()
    @Pattern(regexp = "^MD-[\\d]{4}$",message = "Mã đề thì phải là định dạng MD-XXXX(XXXX là số)")
    private String idName;

    @NotBlank()
    private String nameExam;

    @NotNull
    private int numberQues;

    @NotNull
    private int time;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeQuiz;

    @ManyToOne(targetEntity = Subject.class)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @OneToMany(mappedBy = "exams", cascade = CascadeType.ALL)
    private List<Result> results;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "exam_question",
            joinColumns = @JoinColumn(name = "exam_Id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ques_Id", referencedColumnName = "quesId"))
    private Set<Question> questions;

    public Exam() {
    }

    public Exam(int id, @NotBlank() @Pattern(regexp = "^MD-[\\d]{4}$", message = "Mã đề thì phải là định dạng MD-XXXX(XXXX là số)") String idName,
                @NotBlank() String nameExam, @NotBlank int numberQues, @NotNull int time,
                Subject subject, LocalDateTime timeQuiz,
                List<Result> results, Set<Question> questions) {
        this.id = id;
        this.idName = idName;
        this.nameExam = nameExam;
        this.time = time;
        this.numberQues = numberQues;
        this.subject = subject;
        this.timeQuiz = timeQuiz;

        this.results = results;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getNameExam() {
        return nameExam;
    }

    public void setNameExam(String nameExam) {
        this.nameExam = nameExam;
    }

    public int getNumberQues() {
        return numberQues;
    }

    public void setNumberQues(int numberQues) {
        this.numberQues = numberQues;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public LocalDateTime getTimeQuiz() {
        return timeQuiz;
    }

    public void setTimeQuiz(LocalDateTime timeQuiz) {
        this.timeQuiz = timeQuiz;
    }
}
