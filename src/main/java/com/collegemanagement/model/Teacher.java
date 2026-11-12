package com.collegemanagement.model;

import java.io.Serializable;

public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;

    private int teacherId;
    private String firstName;
    private String lastName;
    private String dateOfJoin;
    private String email;
    private String phoneNumber;

    public Teacher() {}

    public Teacher(int teacherId, String firstName, String lastName, String dateOfJoin, String email, String phoneNumber) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfJoin = dateOfJoin;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }

    public String getDateOfJoin() { return dateOfJoin; }
    public void setDateOfJoin(String dateOfJoin) { this.dateOfJoin = dateOfJoin; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}

