package com.collegemanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int studentId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String admissionDate;
    private int classId;
    private String className;
    private String email;
    private String phoneNumber;
    private String address;
    private String parentName;
    private String status = "Active";

    public Student() {}

    public Student(int studentId, String firstName, String lastName, String dateOfBirth, 
                   String gender, String admissionDate, int classId) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.admissionDate = admissionDate;
        this.classId = classId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName != null ? firstName : "";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName != null ? lastName : "";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return (getFirstName() + " " + getLastName()).trim();
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender != null ? gender : "Other";
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdmissionDate() {
        return admissionDate != null ? admissionDate : LocalDate.now().toString();
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className != null ? className : "B.Tech CSE - 1st Year";
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEmail() {
        if (email != null && !email.isEmpty()) return email;
        if (firstName != null && lastName != null) {
            return (firstName.toLowerCase() + "." + lastName.toLowerCase() + "@college.edu").replaceAll("\\s+", "");
        }
        return "student" + studentId + "@college.edu";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        if (phoneNumber != null && !phoneNumber.isEmpty()) return phoneNumber;
        return "+91 98" + String.format("%08d", Math.abs(studentId * 1337 + 100000) % 100000000);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address != null ? address : "Campus Residential Block A, Room " + (100 + studentId);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParentName() {
        return parentName != null ? parentName : (lastName != null ? lastName + " Guardian" : "Guardian");
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistrationNumber() {
        return "STU-2026-" + String.format("%04d", studentId);
    }
}

