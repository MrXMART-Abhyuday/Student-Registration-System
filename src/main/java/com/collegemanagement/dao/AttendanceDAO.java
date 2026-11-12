package com.collegemanagement.dao;

import com.collegemanagement.model.Attendance;
import com.collegemanagement.model.Student;
import java.sql.*;
import java.util.*;

public class AttendanceDAO {

    public List<Attendance> getAttendanceByDate(String date) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            List<Attendance> list = new ArrayList<>();
            String sql = "SELECT a.attendance_id, a.student_id, a.attendance_date, a.status, " +
                         "CONCAT(s.first_name, ' ', s.last_name) AS student_name " +
                         "FROM Attendance a JOIN Students s ON a.student_id = s.student_id " +
                         "WHERE a.attendance_date = ? ORDER BY a.student_id ASC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, date);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Attendance a = new Attendance();
                        a.setAttendanceId(rs.getInt("attendance_id"));
                        a.setStudentId(rs.getInt("student_id"));
                        a.setAttendanceDate(rs.getString("attendance_date"));
                        a.setStatus(rs.getString("status"));
                        a.setStudentName(rs.getString("student_name"));
                        list.add(a);
                    }
                    return list;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }

        List<Attendance> list = new ArrayList<>();
        for (Attendance a : DBConnection.IN_MEMORY_ATTENDANCE) {
            if (date == null || date.isEmpty() || date.equals(a.getAttendanceDate())) {
                list.add(a);
            }
        }
        return list;
    }

    public List<Attendance> getStudentAttendance(int studentId) {
        List<Attendance> list = new ArrayList<>();
        for (Attendance a : DBConnection.IN_MEMORY_ATTENDANCE) {
            if (a.getStudentId() == studentId) {
                list.add(a);
            }
        }
        list.sort((a, b) -> b.getAttendanceDate().compareTo(a.getAttendanceDate()));
        return list;
    }

    public double getStudentAttendancePercentage(int studentId) {
        List<Attendance> records = getStudentAttendance(studentId);
        if (records.isEmpty()) return 100.0;
        int present = 0;
        for (Attendance a : records) {
            if ("Present".equalsIgnoreCase(a.getStatus())) present++;
            else if ("Late".equalsIgnoreCase(a.getStatus())) present += 1; // Or partial
        }
        return Math.round((present * 100.0 / records.size()) * 10.0) / 10.0;
    }

    public Map<String, Integer> getOverallStats() {
        int present = 0;
        int absent = 0;
        int late = 0;
        for (Attendance a : DBConnection.IN_MEMORY_ATTENDANCE) {
            if ("Present".equalsIgnoreCase(a.getStatus())) present++;
            else if ("Absent".equalsIgnoreCase(a.getStatus())) absent++;
            else if ("Late".equalsIgnoreCase(a.getStatus())) late++;
        }
        Map<String, Integer> stats = new HashMap<>();
        stats.put("present", present);
        stats.put("absent", absent);
        stats.put("late", late);
        stats.put("total", present + absent + late);
        return stats;
    }

    public boolean markAttendance(int studentId, String date, String status) {
        // Remove existing if any
        DBConnection.IN_MEMORY_ATTENDANCE.removeIf(a -> a.getStudentId() == studentId && a.getAttendanceDate().equals(date));
        
        Attendance att = new Attendance(DBConnection.getNextAttendanceId(), studentId, date, status);
        Student s = DBConnection.IN_MEMORY_STUDENTS.get(studentId);
        if (s != null) att.setStudentName(s.getFullName());
        DBConnection.IN_MEMORY_ATTENDANCE.add(att);
        return true;
    }
}

