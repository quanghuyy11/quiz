package com.example.doantotnghiep.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Component
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private double totalCorrect;

    private double score;

    private int error;

    private Timestamp timeStart;

    private Timestamp timeEnd;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "id_user",referencedColumnName = "id" )
    private User users;

    @ManyToOne(targetEntity = Exam.class)
    @JoinColumn(name = "id_exam",referencedColumnName = "id")
    private Exam exams;

    public Result() {
        super();
    }

    public Result(int id, String username, double totalCorrect, User users, Exam exams) {
        this.id = id;
        this.username = username;
        this.totalCorrect = totalCorrect;
        this.users = users;
        this.exams = exams;
    }

    public Result(int id, String username, double totalCorrect, double score, int error,
                  Timestamp timeStart, Timestamp timeEnd, User users, Exam exams) {
        this.id = id;
        this.username = username;
        this.totalCorrect = totalCorrect;
        this.score = score;
        this.error = error;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.users = users;
        this.exams = exams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(double totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Exam getExams() {
        return exams;
    }

    public void setExams(Exam exams) {
        this.exams = exams;
    }
}
