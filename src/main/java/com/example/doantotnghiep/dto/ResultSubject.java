package com.example.doantotnghiep.dto;

public class ResultSubject {
    private String name;

    private double score;

    private int id;

    public ResultSubject(){
    }

    public ResultSubject(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public ResultSubject(String name, double score, int id) {
        this.name = name;
        this.score = score;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
