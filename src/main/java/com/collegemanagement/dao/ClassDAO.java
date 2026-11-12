package com.collegemanagement.dao;

import com.collegemanagement.model.*;
import java.sql.*;
import java.util.*;

public class ClassDAO {

    public List<CollegeClass> getAllClasses() {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            List<CollegeClass> list = new ArrayList<>();
            String sql = "SELECT c.class_id, c.class_name, c.class_teacher_id, " +
                         "CONCAT(t.first_name, ' ', t.last_name) AS teacher_name, " +
                         "(SELECT COUNT(*) FROM Students s WHERE s.class_id = c.class_id) as student_count " +
                         "FROM Classes c LEFT JOIN Teachers t ON c.class_teacher_id = t.teacher_id";
            try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    CollegeClass c = new CollegeClass();
                    c.setClassId(rs.getInt("class_id"));
                    c.setClassName(rs.getString("class_name"));
                    int tid = rs.getInt("class_teacher_id");
                    if (!rs.wasNull()) c.setClassTeacherId(tid);
                    c.setClassTeacherName(rs.getString("teacher_name"));
                    c.setStudentCount(rs.getInt("student_count"));
                    list.add(c);
                }
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }

        List<CollegeClass> list = new ArrayList<>(DBConnection.IN_MEMORY_CLASSES.values());
        for (CollegeClass cc : list) {
            int cnt = 0;
            for (Student s : DBConnection.IN_MEMORY_STUDENTS.values()) {
                if (s.getClassId() == cc.getClassId()) cnt++;
            }
            cc.setStudentCount(cnt);
        }
        list.sort(Comparator.comparingInt(CollegeClass::getClassId));
        return list;
    }

    public CollegeClass getClassById(int classId) {
        for (CollegeClass c : getAllClasses()) {
            if (c.getClassId() == classId) return c;
        }
        return null;
    }

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(DBConnection.IN_MEMORY_SUBJECTS.values());
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(DBConnection.IN_MEMORY_TEACHERS.values());
    }
}

