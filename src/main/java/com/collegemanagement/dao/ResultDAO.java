package com.collegemanagement.dao;

import com.collegemanagement.model.*;
import java.sql.*;
import java.util.*;

public class ResultDAO {

    public List<Exam> getAllExams() {
        return new ArrayList<>(DBConnection.IN_MEMORY_EXAMS.values());
    }

    public Exam getExamById(int examId) {
        return DBConnection.IN_MEMORY_EXAMS.get(examId);
    }

    public List<ExamResult> getResultsByExam(int examId) {
        List<ExamResult> list = new ArrayList<>();
        for (ExamResult er : DBConnection.IN_MEMORY_RESULTS) {
            if (er.getExamId() == examId) {
                list.add(er);
            }
        }
        return list;
    }

    public ReportCard getStudentReportCard(int studentId, int examId) {
        Student stu = DBConnection.IN_MEMORY_STUDENTS.get(studentId);
        if (stu == null) return null;

        Exam ex = DBConnection.IN_MEMORY_EXAMS.get(examId);
        String examName = ex != null ? ex.getExamName() : "Semester Exam";

        ReportCard card = new ReportCard();
        card.setStudentId(studentId);
        card.setStudentName(stu.getFullName());
        card.setClassName(stu.getClassName());
        card.setExamName(examName);

        for (ExamResult er : DBConnection.IN_MEMORY_RESULTS) {
            if (er.getStudentId() == studentId && er.getExamId() == examId) {
                card.addSubject(er);
            }
        }

        // If no exam results found for this exam, generate standard curriculum subjects with default marks
        if (card.getSubjects().isEmpty()) {
            for (Subject sub : DBConnection.IN_MEMORY_SUBJECTS.values()) {
                ExamResult er = new ExamResult(DBConnection.getNextResultId(), studentId, examId, sub.getSubjectId(), 80.0, 100);
                er.setStudentName(stu.getFullName());
                er.setExamName(examName);
                er.setSubjectName(sub.getSubjectName());
                card.addSubject(er);
            }
        }

        return card;
    }

    public List<ReportCard> getAllReportCards(int examId) {
        List<ReportCard> list = new ArrayList<>();
        for (Student stu : DBConnection.IN_MEMORY_STUDENTS.values()) {
            ReportCard rc = getStudentReportCard(stu.getStudentId(), examId);
            if (rc != null) list.add(rc);
        }
        list.sort((a, b) -> Double.compare(b.getOverallPercentage(), a.getOverallPercentage()));
        return list;
    }

    public boolean saveResult(int studentId, int examId, int subjectId, double marks, int maxMarks) {
        // Update if existing
        for (ExamResult er : DBConnection.IN_MEMORY_RESULTS) {
            if (er.getStudentId() == studentId && er.getExamId() == examId && er.getSubjectId() == subjectId) {
                er.setMarksObtained(marks);
                er.setMaxMarks(maxMarks);
                return true;
            }
        }

        Student s = DBConnection.IN_MEMORY_STUDENTS.get(studentId);
        Exam ex = DBConnection.IN_MEMORY_EXAMS.get(examId);
        Subject sub = DBConnection.IN_MEMORY_SUBJECTS.get(subjectId);

        ExamResult er = new ExamResult(DBConnection.getNextResultId(), studentId, examId, subjectId, marks, maxMarks);
        if (s != null) er.setStudentName(s.getFullName());
        if (ex != null) er.setExamName(ex.getExamName());
        if (sub != null) er.setSubjectName(sub.getSubjectName());

        DBConnection.IN_MEMORY_RESULTS.add(er);
        return true;
    }
}

