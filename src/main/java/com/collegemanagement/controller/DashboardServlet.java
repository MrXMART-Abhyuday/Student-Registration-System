package com.collegemanagement.controller;

import com.collegemanagement.dao.*;
import com.collegemanagement.model.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO = new StudentDAO();
    private ClassDAO classDAO = new ClassDAO();
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private ResultDAO resultDAO = new ResultDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Collect metrics
        int totalStudents = studentDAO.getTotalCount();
        int totalClasses = classDAO.getAllClasses().size();
        int totalTeachers = classDAO.getAllTeachers().size();
        
        Map<String, Integer> attendanceStats = attendanceDAO.getOverallStats();
        int presentCount = attendanceStats.getOrDefault("present", 0);
        int totalAttendance = attendanceStats.getOrDefault("total", 1);
        double attendanceRate = Math.round((presentCount * 100.0 / (totalAttendance > 0 ? totalAttendance : 1)) * 10.0) / 10.0;

        List<Student> recentStudents = studentDAO.getRecentRegistrations(5);
        List<ReportCard> topPerformers = resultDAO.getAllReportCards(1);
        if (topPerformers.size() > 5) {
            topPerformers = topPerformers.subList(0, 5);
        }

        req.setAttribute("totalStudents", totalStudents);
        req.setAttribute("totalClasses", totalClasses);
        req.setAttribute("totalTeachers", totalTeachers);
        req.setAttribute("attendanceRate", attendanceRate);
        req.setAttribute("recentStudents", recentStudents);
        req.setAttribute("topPerformers", topPerformers);
        req.setAttribute("isDbConnected", DBConnection.isMysqlConnected());

        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}

