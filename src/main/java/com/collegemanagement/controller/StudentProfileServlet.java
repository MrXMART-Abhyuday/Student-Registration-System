package com.collegemanagement.controller;

import com.collegemanagement.dao.AttendanceDAO;
import com.collegemanagement.dao.ResultDAO;
import com.collegemanagement.dao.StudentDAO;
import com.collegemanagement.model.Attendance;
import com.collegemanagement.model.ReportCard;
import com.collegemanagement.model.Student;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO = new StudentDAO();
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private ResultDAO resultDAO = new ResultDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/students");
            return;
        }

        try {
            int studentId = Integer.parseInt(idStr);
            Student student = studentDAO.getStudentById(studentId);
            if (student == null) {
                resp.sendRedirect(req.getContextPath() + "/students?msg=Student+not+found");
                return;
            }

            List<Attendance> attendanceHistory = attendanceDAO.getStudentAttendance(studentId);
            double attendancePercentage = attendanceDAO.getStudentAttendancePercentage(studentId);
            ReportCard reportCard = resultDAO.getStudentReportCard(studentId, 1);

            req.setAttribute("student", student);
            req.setAttribute("attendanceHistory", attendanceHistory);
            req.setAttribute("attendancePercentage", attendancePercentage);
            req.setAttribute("reportCard", reportCard);

            req.getRequestDispatcher("/WEB-INF/views/student-profile.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/students");
        }
    }
}

