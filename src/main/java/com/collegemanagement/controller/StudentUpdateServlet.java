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

public class StudentUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO = new StudentDAO();
    private ClassDAO classDAO = new ClassDAO();

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
                resp.sendRedirect(req.getContextPath() + "/students");
                return;
            }

            List<CollegeClass> classes = classDAO.getAllClasses();
            req.setAttribute("student", student);
            req.setAttribute("classes", classes);
            req.getRequestDispatcher("/WEB-INF/views/student-edit.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/students");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("studentId");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dateOfBirth = req.getParameter("dateOfBirth");
        String gender = req.getParameter("gender");
        String classIdStr = req.getParameter("classId");
        String admissionDate = req.getParameter("admissionDate");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");
        String parentName = req.getParameter("parentName");

        try {
            int studentId = Integer.parseInt(idStr);
            int classId = Integer.parseInt(classIdStr);

            Student student = new Student();
            student.setStudentId(studentId);
            student.setFirstName(firstName.trim());
            student.setLastName(lastName.trim());
            student.setDateOfBirth(dateOfBirth.trim());
            student.setGender(gender != null ? gender.trim() : "Other");
            student.setClassId(classId);
            student.setAdmissionDate(admissionDate.trim());
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
            student.setAddress(address);
            student.setParentName(parentName);

            boolean updated = studentDAO.updateStudent(student);
            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/student/profile?id=" + studentId + "&msg=Student+updated+successfully");
            } else {
                req.setAttribute("errorMessage", "Could not update student details.");
                doGet(req, resp);
            }
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/students");
        }
    }
}

