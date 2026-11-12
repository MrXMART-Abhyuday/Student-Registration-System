package com.collegemanagement.controller;

import com.collegemanagement.dao.ClassDAO;
import com.collegemanagement.dao.StudentDAO;
import com.collegemanagement.model.CollegeClass;
import com.collegemanagement.model.Student;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO = new StudentDAO();
    private ClassDAO classDAO = new ClassDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        String classIdStr = req.getParameter("classId");
        String gender = req.getParameter("gender");
        String msg = req.getParameter("msg");

        Integer classId = null;
        if (classIdStr != null && !classIdStr.isEmpty() && !"All".equalsIgnoreCase(classIdStr)) {
            try {
                classId = Integer.parseInt(classIdStr);
            } catch (NumberFormatException ignored) {}
        }

        List<Student> students = studentDAO.searchStudents(query, classId, gender);
        List<CollegeClass> classes = classDAO.getAllClasses();

        req.setAttribute("students", students);
        req.setAttribute("classes", classes);
        req.setAttribute("query", query != null ? query : "");
        req.setAttribute("selectedClassId", classId != null ? classId : 0);
        req.setAttribute("selectedGender", gender != null ? gender : "All");
        req.setAttribute("infoMessage", msg);

        req.getRequestDispatcher("/WEB-INF/views/student-list.jsp").forward(req, resp);
    }
}

