package com.collegemanagement.dao;

import com.collegemanagement.model.Student;
import java.sql.*;
import java.util.*;

public class StudentDAO {

    public List<Student> getAllStudents() {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            List<Student> list = new ArrayList<>();
            String sql = "SELECT s.student_id, s.first_name, s.last_name, s.date_of_birth, s.gender, s.admission_date, s.class_id, c.class_name " +
                         "FROM Students s LEFT JOIN Classes c ON s.class_id = c.class_id ORDER BY s.student_id ASC";
            try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setDateOfBirth(rs.getString("date_of_birth"));
                    s.setGender(rs.getString("gender"));
                    s.setAdmissionDate(rs.getString("admission_date"));
                    s.setClassId(rs.getInt("class_id"));
                    s.setClassName(rs.getString("class_name"));
                    list.add(s);
                }
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }
        
        // In-memory fallback
        List<Student> result = new ArrayList<>(DBConnection.IN_MEMORY_STUDENTS.values());
        result.sort(Comparator.comparingInt(Student::getStudentId));
        return result;
    }

    public List<Student> searchStudents(String query, Integer classId, String gender) {
        List<Student> all = getAllStudents();
        List<Student> filtered = new ArrayList<>();

        String q = query != null ? query.trim().toLowerCase() : "";

        for (Student s : all) {
            boolean matchesQuery = q.isEmpty() ||
                    s.getFullName().toLowerCase().contains(q) ||
                    String.valueOf(s.getStudentId()).contains(q) ||
                    s.getRegistrationNumber().toLowerCase().contains(q) ||
                    s.getEmail().toLowerCase().contains(q);

            boolean matchesClass = (classId == null || classId <= 0 || s.getClassId() == classId);
            boolean matchesGender = (gender == null || gender.isEmpty() || "All".equalsIgnoreCase(gender) || s.getGender().equalsIgnoreCase(gender));

            if (matchesQuery && matchesClass && matchesGender) {
                filtered.add(s);
            }
        }
        return filtered;
    }

    public Student getStudentById(int studentId) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            String sql = "SELECT s.student_id, s.first_name, s.last_name, s.date_of_birth, s.gender, s.admission_date, s.class_id, c.class_name " +
                         "FROM Students s LEFT JOIN Classes c ON s.class_id = c.class_id WHERE s.student_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Student s = new Student();
                        s.setStudentId(rs.getInt("student_id"));
                        s.setFirstName(rs.getString("first_name"));
                        s.setLastName(rs.getString("last_name"));
                        s.setDateOfBirth(rs.getString("date_of_birth"));
                        s.setGender(rs.getString("gender"));
                        s.setAdmissionDate(rs.getString("admission_date"));
                        s.setClassId(rs.getInt("class_id"));
                        s.setClassName(rs.getString("class_name"));
                        return s;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }
        return DBConnection.IN_MEMORY_STUDENTS.get(studentId);
    }

    public boolean addStudent(Student student) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            String sql = "INSERT INTO Students (first_name, last_name, date_of_birth, gender, admission_date, class_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, student.getFirstName());
                ps.setString(2, student.getLastName());
                ps.setString(3, student.getDateOfBirth());
                ps.setString(4, student.getGender());
                ps.setString(5, student.getAdmissionDate());
                ps.setInt(6, student.getClassId() > 0 ? student.getClassId() : 1);
                
                int affected = ps.executeUpdate();
                if (affected > 0) {
                    try (ResultSet gk = ps.getGeneratedKeys()) {
                        if (gk.next()) {
                            student.setStudentId(gk.getInt(1));
                        }
                    }
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }

        // In-memory addition
        int newId = DBConnection.getNextStudentId();
        student.setStudentId(newId);
        if (student.getClassName() == null || student.getClassName().isEmpty()) {
            var cls = DBConnection.IN_MEMORY_CLASSES.get(student.getClassId());
            student.setClassName(cls != null ? cls.getClassName() : "B.Tech CSE - 1st Year");
        }
        DBConnection.IN_MEMORY_STUDENTS.put(newId, student);

        // Also append SQL insert statement to NEW_REGISTERED_STUDENTS.sql for easy SQL inspection
        appendStudentToSqlFile(student);

        return true;
    }

    private void appendStudentToSqlFile(Student s) {
        try {
            String sqlLine = String.format(
                "INSERT INTO Students (first_name, last_name, date_of_birth, gender, admission_date, class_id) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', %d); -- student_id %d: %s\n",
                s.getFirstName().replace("'", "''"),
                s.getLastName().replace("'", "''"),
                s.getDateOfBirth(),
                s.getGender(),
                s.getAdmissionDate(),
                s.getClassId() > 0 ? s.getClassId() : 1,
                s.getStudentId(),
                s.getFullName()
            );
            java.nio.file.Files.write(
                java.nio.file.Paths.get("NEW_REGISTERED_STUDENTS.sql"),
                sqlLine.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND
            );
        } catch (Exception ignored) {}
    }

    public boolean updateStudent(Student student) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            String sql = "UPDATE Students SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, admission_date = ?, class_id = ? WHERE student_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, student.getFirstName());
                ps.setString(2, student.getLastName());
                ps.setString(3, student.getDateOfBirth());
                ps.setString(4, student.getGender());
                ps.setString(5, student.getAdmissionDate());
                ps.setInt(6, student.getClassId());
                ps.setInt(7, student.getStudentId());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }

        // In-memory update
        if (DBConnection.IN_MEMORY_STUDENTS.containsKey(student.getStudentId())) {
            var cls = DBConnection.IN_MEMORY_CLASSES.get(student.getClassId());
            if (cls != null) student.setClassName(cls.getClassName());
            DBConnection.IN_MEMORY_STUDENTS.put(student.getStudentId(), student);
            return true;
        }
        return false;
    }

    public boolean deleteStudent(int studentId) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            String sql = "DELETE FROM Students WHERE student_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }

        return DBConnection.IN_MEMORY_STUDENTS.remove(studentId) != null;
    }

    public int getTotalCount() {
        return getAllStudents().size();
    }

    public List<Student> getRecentRegistrations(int limit) {
        List<Student> all = getAllStudents();
        all.sort((a, b) -> Integer.compare(b.getStudentId(), a.getStudentId()));
        if (all.size() <= limit) return all;
        return all.subList(0, limit);
    }
}

