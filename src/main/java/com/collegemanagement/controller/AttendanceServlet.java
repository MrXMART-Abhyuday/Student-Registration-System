package com.collegemanagement.controller;

import com.collegemanagement.dao.AttendanceDAO;
import com.collegemanagement.dao.StudentDAO;
import com.collegemanagement.model.Attendance;
import com.collegemanagement.model.Student;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");
        if (date == null || date.trim().isEmpty()) {
            date = "2025-09-01"; // Default date matching the sample data
        }

        List<Attendance> attendanceList = attendanceDAO.getAttendanceByDate(date);
        List<Student> allStudents = studentDAO.getAllStudents();
        Map<String, Integer> stats = attendanceDAO.getOverallStats();

        req.setAttribute("selectedDate", date);
        req.setAttribute("attendanceList", attendanceList);
        req.setAttribute("allStudents", allStudents);
        req.setAttribute("stats", stats);

        req.getRequestDispatcher("/WEB-INF/views/attendance.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentIdStr = req.getParameter("studentId");
        String date = req.getParameter("attendanceDate");
        String status = req.getParameter("status");

        if (studentIdStr != null && date != null && status != null) {
            try {
                int studentId = Integer.parseInt(studentIdStr);
                attendanceDAO.markAttendance(studentId, date, status);
            } catch (NumberFormatException ignored) {}
        }

        resp.sendRedirect(req.getContextPath() + "/attendance?date=" + (date != null ? date : "2025-09-01"));
    }
}

