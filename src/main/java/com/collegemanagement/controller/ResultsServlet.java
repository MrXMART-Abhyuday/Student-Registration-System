package com.collegemanagement.controller;

import com.collegemanagement.dao.ClassDAO;
import com.collegemanagement.dao.ResultDAO;
import com.collegemanagement.dao.StudentDAO;
import com.collegemanagement.model.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResultsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ResultDAO resultDAO = new ResultDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private ClassDAO classDAO = new ClassDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String examIdStr = req.getParameter("examId");
        int examId = 1;
        if (examIdStr != null && !examIdStr.isEmpty()) {
            try {
                examId = Integer.parseInt(examIdStr);
            } catch (NumberFormatException ignored) {}
        }

        List<Exam> exams = resultDAO.getAllExams();
        Exam currentExam = resultDAO.getExamById(examId);
        List<ReportCard> rankings = resultDAO.getAllReportCards(examId);
        List<Student> students = studentDAO.getAllStudents();
        List<Subject> subjects = classDAO.getAllSubjects();

        req.setAttribute("exams", exams);
        req.setAttribute("selectedExamId", examId);
        req.setAttribute("currentExam", currentExam);
        req.setAttribute("rankings", rankings);
        req.setAttribute("students", students);
        req.setAttribute("subjects", subjects);

        req.getRequestDispatcher("/WEB-INF/views/results.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentIdStr = req.getParameter("studentId");
        String examIdStr = req.getParameter("examId");
        String subjectIdStr = req.getParameter("subjectId");
        String marksStr = req.getParameter("marks");

        if (studentIdStr != null && examIdStr != null && subjectIdStr != null && marksStr != null) {
            try {
                int studentId = Integer.parseInt(studentIdStr);
                int examId = Integer.parseInt(examIdStr);
                int subjectId = Integer.parseInt(subjectIdStr);
                double marks = Double.parseDouble(marksStr);

                resultDAO.saveResult(studentId, examId, subjectId, marks, 100);
            } catch (NumberFormatException ignored) {}
        }

        resp.sendRedirect(req.getContextPath() + "/results?examId=" + (examIdStr != null ? examIdStr : "1"));
    }
}

