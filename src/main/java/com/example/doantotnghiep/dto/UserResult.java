package com.example.doantotnghiep.dto;

import java.util.Date;

public class UserResult {
    private int id;

    private String name;

    private double score;

    private int error;

    private Date timeStart;

    private Date timeEnd;

    public UserResult(int id, String name, double score, int error, Date timeStart, Date timeEnd) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.error = error;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public int getError() {
        return error;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }
}
