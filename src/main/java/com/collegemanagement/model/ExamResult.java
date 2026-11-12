package com.collegemanagement.model;

import java.io.Serializable;

public class ExamResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private int resultId;
    private int studentId;
    private String studentName;
    private int examId;
    private String examName;
    private int subjectId;
    private String subjectName;
    private double marksObtained;
    private int maxMarks = 100;

    public ExamResult() {}

    public ExamResult(int resultId, int studentId, int examId, int subjectId, double marksObtained, int maxMarks) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.examId = examId;
        this.subjectId = subjectId;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName != null ? studentName : "Student #" + studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public String getExamName() { return examName != null ? examName : "Semester Exam"; }
    public void setExamName(String examName) { this.examName = examName; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName != null ? subjectName : "Subject #" + subjectId; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(double marksObtained) { this.marksObtained = marksObtained; }

    public int getMaxMarks() { return maxMarks; }
    public void setMaxMarks(int maxMarks) { this.maxMarks = maxMarks; }

    public double getPercentage() {
        if (maxMarks <= 0) return 0.0;
        return Math.round((marksObtained / maxMarks) * 1000.0) / 10.0;
    }

    public String getGrade() {
        double p = getPercentage();
        if (p >= 90) return "A+";
        if (p >= 80) return "A";
        if (p >= 70) return "B+";
        if (p >= 60) return "B";
        if (p >= 50) return "C";
        if (p >= 40) return "D";
        return "F";
    }

    public String getStatus() {
        return getPercentage() >= 40.0 ? "Pass" : "Fail";
    }
}

