package com.collegemanagement.controller;

import com.collegemanagement.dao.ResultDAO;
import com.collegemanagement.dao.StudentDAO;
import com.collegemanagement.model.ReportCard;
import com.collegemanagement.model.Student;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReportCardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ResultDAO resultDAO = new ResultDAO();
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String examIdStr = req.getParameter("examId");

        int studentId = 1;
        int examId = 1;

        if (idStr != null && !idStr.isEmpty()) {
            try { studentId = Integer.parseInt(idStr); } catch (NumberFormatException ignored) {}
        }
        if (examIdStr != null && !examIdStr.isEmpty()) {
            try { examId = Integer.parseInt(examIdStr); } catch (NumberFormatException ignored) {}
        }

        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            resp.sendRedirect(req.getContextPath() + "/students");
            return;
        }

        ReportCard reportCard = resultDAO.getStudentReportCard(studentId, examId);

        req.setAttribute("student", student);
        req.setAttribute("reportCard", reportCard);
        req.setAttribute("selectedExamId", examId);

        req.getRequestDispatcher("/WEB-INF/views/report-card.jsp").forward(req, resp);
    }
}

