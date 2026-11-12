package com.collegemanagement.model;

import java.io.Serializable;

public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;

    private int examId;
    private String examName;
    private String examDate;

    public Exam() {}

    public Exam(int examId, String examName, String examDate) {
        this.examId = examId;
        this.examName = examName;
        this.examDate = examDate;
    }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }

    public String getExamDate() { return examDate; }
    public void setExamDate(String examDate) { this.examDate = examDate; }
}

