package com.collegemanagement.model;

import java.io.Serializable;

public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;

    private int attendanceId;
    private int studentId;
    private String studentName;
    private String attendanceDate;
    private String status; // Present, Absent, Late

    public Attendance() {}

    public Attendance(int attendanceId, int studentId, String attendanceDate, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName != null ? studentName : "Student #" + studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(String attendanceDate) { this.attendanceDate = attendanceDate; }

    public String getStatus() { return status != null ? status : "Present"; }
    public void setStatus(String status) { this.status = status; }
}

