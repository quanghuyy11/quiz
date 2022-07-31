package com.example.doantotnghiep.model;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Component
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quesId;

    @NotBlank(message = "Câu hỏi không được để trống.")
    private String title;

    @NotBlank(message = "Câu trả lời không được để trống.")
    private String optionA;

    @NotBlank(message = "Câu trả lời không được để trống.")
    private String optionB;

    @NotBlank(message = "Câu trả lời không được để trống.")
    private String optionC;

    @NotBlank(message = "Câu trả lời không được để trống.")
    private String optionD;

    private String optionE;

    private String optionF;

    @NotNull
    private String ans;

    private String ans1;

    private String ans2;

    private int answerNumb;

    @NotNull
    private String chose;

    @ManyToOne(targetEntity = Subject.class)
    @JoinColumn(name = "id_subject", referencedColumnName = "id")
    private Subject subjects;

    @ManyToMany(mappedBy = "questions")
    private Set<Exam> exams;

    public Question() {
        super();
    }

    public Question(int quesId, String title, String optionA, String optionB,
                    String optionC, String optionD, String optionE, String optionF,
                    String ans, String ans1, String ans2, int answerNumb, String chose,
                    Subject subjects, Set<Exam> exams) {
        this.quesId = quesId;
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.optionF = optionF;
        this.ans = ans;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.chose = chose;
        this.answerNumb = answerNumb;
        this.subjects = subjects;
        this.exams = exams;
    }

    public int getQuesId() {
        return quesId;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getOptionF() {
        return optionF;
    }

    public void setOptionF(String optionF) {
        this.optionF = optionF;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public int getAnswerNumb() {
        return answerNumb;
    }

    public void setAnswerNumb(int answerNumb) {
        this.answerNumb = answerNumb;
    }

    public String getChose() {
        return chose;
    }

    public void setChose(String chose) {
        this.chose = chose;
    }

    public Subject getSubject() {
        return subjects;
    }

    public void setSubject(Subject subject) {
        this.subjects = subject;
    }

    @Override
    public String toString() {
        return "Question{" +
                "quesId=" + quesId +
                ", title='" + title + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", optionE='" + optionE + '\'' +
                ", optionF='" + optionF + '\'' +
                ", ans=" + ans +
                ", ans1=" + ans1 +
                ", ans2=" + ans2 +
                ", chose=" + chose +
                ", subjects=" + subjects +
                ", exams=" + exams +
                '}';
    }
}
