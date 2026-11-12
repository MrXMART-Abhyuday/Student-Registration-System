package com.collegemanagement.model;

import java.io.Serializable;

public class CollegeClass implements Serializable {
    private static final long serialVersionUID = 1L;

    private int classId;
    private String className;
    private Integer classTeacherId;
    private String classTeacherName;
    private int studentCount;

    public CollegeClass() {}

    public CollegeClass(int classId, String className, Integer classTeacherId) {
        this.classId = classId;
        this.className = className;
        this.classTeacherId = classTeacherId;
    }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Integer getClassTeacherId() { return classTeacherId; }
    public void setClassTeacherId(Integer classTeacherId) { this.classTeacherId = classTeacherId; }

    public String getClassTeacherName() { return classTeacherName != null ? classTeacherName : "Not Assigned"; }
    public void setClassTeacherName(String classTeacherName) { this.classTeacherName = classTeacherName; }

    public int getStudentCount() { return studentCount; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }
}

