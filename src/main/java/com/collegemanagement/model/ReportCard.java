package com.collegemanagement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private int studentId;
    private String studentName;
    private String className;
    private String examName;
    private List<ExamResult> subjects = new ArrayList<>();
    private double totalMarksObtained;
    private int totalMaxMarks;
    private double overallPercentage;
    private String overallGrade;
    private String status;

    public ReportCard() {}

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }

    public List<ExamResult> getSubjects() { return subjects; }
    public void setSubjects(List<ExamResult> subjects) {
        this.subjects = subjects;
        calculateTotals();
    }

    public void addSubject(ExamResult result) {
        this.subjects.add(result);
        calculateTotals();
    }

    public void calculateTotals() {
        double obtained = 0;
        int max = 0;
        boolean hasFail = false;
        for (ExamResult r : subjects) {
            obtained += r.getMarksObtained();
            max += r.getMaxMarks();
            if ("Fail".equalsIgnoreCase(r.getStatus())) {
                hasFail = true;
            }
        }
        this.totalMarksObtained = Math.round(obtained * 100.0) / 100.0;
        this.totalMaxMarks = max;
        this.overallPercentage = max > 0 ? Math.round((obtained / max) * 10000.0) / 100.0 : 0.0;
        
        if (overallPercentage >= 90) overallGrade = "A+ (Outstanding)";
        else if (overallPercentage >= 80) overallGrade = "A (Excellent)";
        else if (overallPercentage >= 70) overallGrade = "B+ (Very Good)";
        else if (overallPercentage >= 60) overallGrade = "B (Good)";
        else if (overallPercentage >= 50) overallGrade = "C (Satisfactory)";
        else if (overallPercentage >= 40) overallGrade = "D (Pass)";
        else overallGrade = "F (Needs Improvement)";

        this.status = (hasFail || overallPercentage < 40.0) ? "Needs Attention" : "Passed";
    }

    public double getTotalMarksObtained() { return totalMarksObtained; }
    public int getTotalMaxMarks() { return totalMaxMarks; }
    public double getOverallPercentage() { return overallPercentage; }
    public String getOverallGrade() { return overallGrade; }
    public String getStatus() { return status; }
}

