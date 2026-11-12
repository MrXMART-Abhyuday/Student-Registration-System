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
import java.time.LocalDate;
import java.util.List;

public class StudentRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDAO studentDAO = new StudentDAO();
    private ClassDAO classDAO = new ClassDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CollegeClass> classes = classDAO.getAllClasses();
        req.setAttribute("classes", classes);
        req.setAttribute("todayDate", LocalDate.now().toString());
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

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

        // Basic validation
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            dateOfBirth == null || dateOfBirth.trim().isEmpty() ||
            classIdStr == null || classIdStr.trim().isEmpty()) {

            req.setAttribute("errorMessage", "All required fields marked with * must be filled.");
            doGet(req, resp);
            return;
        }

        int classId = 1;
        try {
            classId = Integer.parseInt(classIdStr);
        } catch (NumberFormatException ignored) {}

        if (admissionDate == null || admissionDate.trim().isEmpty()) {
            admissionDate = LocalDate.now().toString();
        }

        Student student = new Student();
        student.setFirstName(firstName.trim());
        student.setLastName(lastName.trim());
        student.setDateOfBirth(dateOfBirth.trim());
        student.setGender(gender != null ? gender.trim() : "Other");
        student.setClassId(classId);
        student.setAdmissionDate(admissionDate.trim());
        student.setEmail(email != null && !email.trim().isEmpty() ? email.trim() : null);
        student.setPhoneNumber(phoneNumber != null && !phoneNumber.trim().isEmpty() ? phoneNumber.trim() : null);
        student.setAddress(address != null && !address.trim().isEmpty() ? address.trim() : "Campus Hostel");
        student.setParentName(parentName != null && !parentName.trim().isEmpty() ? parentName.trim() : "Parent / Guardian");

        CollegeClass cc = classDAO.getClassById(classId);
        if (cc != null) {
            student.setClassName(cc.getClassName());
        }

        boolean saved = studentDAO.addStudent(student);

        if (saved) {
            req.setAttribute("student", student);
            req.setAttribute("successMessage", "Student registration completed successfully!");
            req.getRequestDispatcher("/WEB-INF/views/registration-success.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMessage", "Failed to save student record to database. Please try again.");
            doGet(req, resp);
        }
    }
}

