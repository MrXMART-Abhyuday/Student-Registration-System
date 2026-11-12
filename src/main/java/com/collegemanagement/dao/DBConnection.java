package com.collegemanagement.dao;

import com.collegemanagement.model.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DBConnection provides JDBC database connectivity with intelligent fallback.
 * If MySQL Server (SchoolManagementDB) is running, it connects via JDBC.
 * If MySQL is unavailable, it seamlessly operates using an in-memory repository
 * pre-populated with the exact 20 students and curriculum from
 * DATABASE_STUDENTS_MANAGEMENT.sql.
 */
public class DBConnection {
    private static String dbUrl = "jdbc:mysql://localhost:3306/SchoolManagementDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String dbUser = "root";
    private static String dbPassword = "Abhyuday@2105"; // Common default, can be overridden via db.properties
    private static boolean mysqlConnected = false;
    private static boolean checkedConnection = false;

    // In-memory backing store for zero-config demonstration and offline reliability
    public static final Map<Integer, Student> IN_MEMORY_STUDENTS = new ConcurrentHashMap<>();
    public static final Map<Integer, CollegeClass> IN_MEMORY_CLASSES = new ConcurrentHashMap<>();
    public static final Map<Integer, Teacher> IN_MEMORY_TEACHERS = new ConcurrentHashMap<>();
    public static final Map<Integer, Subject> IN_MEMORY_SUBJECTS = new ConcurrentHashMap<>();
    public static final List<Attendance> IN_MEMORY_ATTENDANCE = Collections.synchronizedList(new ArrayList<>());
    public static final Map<Integer, Exam> IN_MEMORY_EXAMS = new ConcurrentHashMap<>();
    public static final List<ExamResult> IN_MEMORY_RESULTS = Collections.synchronizedList(new ArrayList<>());
    public static final Map<String, User> IN_MEMORY_USERS = new ConcurrentHashMap<>();

    private static final AtomicInteger studentIdSeq = new AtomicInteger(20);
    private static final AtomicInteger attendanceIdSeq = new AtomicInteger(40);
    private static final AtomicInteger resultIdSeq = new AtomicInteger(100);

    static {
        loadProperties();
        initializeDefaultData();
    }

    private static void loadProperties() {
        try (InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                if (props.getProperty("db.url") != null)
                    dbUrl = props.getProperty("db.url");
                if (props.getProperty("db.user") != null)
                    dbUser = props.getProperty("db.user");
                if (props.getProperty("db.password") != null)
                    dbPassword = props.getProperty("db.password");
            }
        } catch (Exception ignored) {
        }
    }

    public static synchronized Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            mysqlConnected = true;
            checkedConnection = true;
            return conn;
        } catch (Exception e) {
            mysqlConnected = false;
            checkedConnection = true;
            return null;
        }
    }

    public static boolean isMysqlConnected() {
        if (!checkedConnection) {
            Connection c = getConnection();
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException ignored) {
                }
            }
        }
        return mysqlConnected;
    }

    public static int getNextStudentId() {
        return studentIdSeq.incrementAndGet();
    }

    public static int getNextAttendanceId() {
        return attendanceIdSeq.incrementAndGet();
    }

    public static int getNextResultId() {
        return resultIdSeq.incrementAndGet();
    }

    private static void initializeDefaultData() {
        // 1. Teachers
        IN_MEMORY_TEACHERS.put(1,
                new Teacher(1, "Aarav", "Mehta", "2015-07-20", "aarav.mehta@school.edu", "9812011001"));
        IN_MEMORY_TEACHERS.put(2,
                new Teacher(2, "Priya", "Sharma", "2018-08-15", "priya.sharma@school.edu", "9812011002"));
        IN_MEMORY_TEACHERS.put(3,
                new Teacher(3, "Rajesh", "Kumar", "2019-01-10", "rajesh.kumar@school.edu", "9812011003"));
        IN_MEMORY_TEACHERS.put(4,
                new Teacher(4, "Sonia", "Verma", "2021-03-01", "sonia.verma@school.edu", "9812011004"));
        IN_MEMORY_TEACHERS.put(5,
                new Teacher(5, "Vikram", "Singh", "2017-11-25", "vikram.singh@school.edu", "9812011005"));
        IN_MEMORY_TEACHERS.put(6,
                new Teacher(6, "Neelam", "Yadav", "2022-06-10", "neelam.yadav@school.edu", "9812011006"));

        // 2. Classes
        CollegeClass c1 = new CollegeClass(1, "B.Tech CSE - 1st Year", 1);
        c1.setClassTeacherName("Prof. Aarav Mehta");
        c1.setStudentCount(20);
        IN_MEMORY_CLASSES.put(1, c1);

        CollegeClass c2 = new CollegeClass(2, "B.Tech IT - 1st Year", 2);
        c2.setClassTeacherName("Prof. Priya Sharma");
        IN_MEMORY_CLASSES.put(2, c2);

        CollegeClass c3 = new CollegeClass(3, "BCA - 1st Year", 4);
        c3.setClassTeacherName("Prof. Sonia Verma");
        IN_MEMORY_CLASSES.put(3, c3);

        // 3. Subjects
        Subject s1 = new Subject(1, "Data Structures");
        s1.setTeacherName("Prof. Priya Sharma");
        Subject s2 = new Subject(2, "Discrete Mathematics");
        s2.setTeacherName("Prof. Vikram Singh");
        Subject s3 = new Subject(3, "Programming in Python");
        s3.setTeacherName("Prof. Aarav Mehta");
        Subject s4 = new Subject(4, "English Communication");
        s4.setTeacherName("Prof. Sonia Verma");

        IN_MEMORY_SUBJECTS.put(1, s1);
        IN_MEMORY_SUBJECTS.put(2, s2);
        IN_MEMORY_SUBJECTS.put(3, s3);
        IN_MEMORY_SUBJECTS.put(4, s4);

        // 4. The 20 Students from DATABASE_STUDENTS_MANAGEMENT.sql
        Object[][] studentRows = {
                { 1, "Rohan", "Gupta", "2004-03-10", "Male", "2023-07-15", 1, "rohan.gupta@college.edu",
                        "+91 9811002201", "Sector 14, Rohini, New Delhi", "Rajesh Gupta" },
                { 2, "Anjali", "Verma", "2004-08-25", "Female", "2023-07-15", 1, "anjali.verma@college.edu",
                        "+91 9811002202", "Civil Lines, Jaipur, Rajasthan", "Manoj Verma" },
                { 3, "Kunal", "Jain", "2003-11-01", "Male", "2023-08-01", 1, "kunal.jain@college.edu", "+91 9811002203",
                        "Koramangala, Bengaluru, Karnataka", "Sanjay Jain" },
                { 4, "Neha", "Singh", "2004-01-12", "Female", "2023-07-15", 1, "neha.singh@college.edu",
                        "+91 9811002204", "Gomti Nagar, Lucknow, UP", "Alok Singh" },
                { 5, "Vivek", "Reddy", "2003-05-30", "Male", "2023-07-16", 1, "vivek.reddy@college.edu",
                        "+91 9811002205", "Banjara Hills, Hyderabad, Telangana", "K. Reddy" },
                { 6, "Shruti", "Patel", "2004-04-18", "Female", "2023-07-15", 1, "shruti.patel@college.edu",
                        "+91 9811002206", "Satellite, Ahmedabad, Gujarat", "Bhavesh Patel" },
                { 7, "Manish", "Tiwari", "2003-12-05", "Male", "2023-07-15", 1, "manish.tiwari@college.edu",
                        "+91 9811002207", "Kalyan Nagar, Kanpur, UP", "Ramesh Tiwari" },
                { 8, "Deepika", "Yadav", "2004-02-28", "Female", "2023-08-01", 1, "deepika.yadav@college.edu",
                        "+91 9811002208", "DLF Phase 2, Gurugram, Haryana", "Virender Yadav" },
                { 9, "Gaurav", "Das", "2003-10-10", "Male", "2023-07-15", 1, "gaurav.das@college.edu", "+91 9811002209",
                        "Salt Lake, Sector 5, Kolkata, WB", "Subhash Das" },
                { 10, "Sanya", "Malik", "2004-06-03", "Female", "2023-07-16", 1, "sanya.malik@college.edu",
                        "+91 9811002210", "Model Town, Ludhiana, Punjab", "Hardeep Malik" },
                { 11, "Akash", "Choudhary", "2003-07-01", "Male", "2022-07-20", 1, "akash.choudhary@college.edu",
                        "+91 9811002211", "Malviya Nagar, Jaipur, Rajasthan", "P. Choudhary" },
                { 12, "Diya", "Saxena", "2003-10-14", "Female", "2022-07-20", 1, "diya.saxena@college.edu",
                        "+91 9811002212", "Indira Nagar, Dehradun, UK", "Anurag Saxena" },
                { 13, "Harman", "Kaur", "2003-09-09", "Female", "2022-07-25", 1, "harman.kaur@college.edu",
                        "+91 9811002213", "Sector 35, Chandigarh, UT", "Gurpreet Kaur" },
                { 14, "Ishaan", "Walia", "2004-01-20", "Male", "2022-07-20", 1, "ishaan.walia@college.edu",
                        "+91 9811002214", "Vasant Kunj, New Delhi", "Sunil Walia" },
                { 15, "Jiya", "Bhatia", "2003-12-04", "Female", "2022-08-01", 1, "jiya.bhatia@college.edu",
                        "+91 9811002215", "Andheri West, Mumbai, Maharashtra", "Deepak Bhatia" },
                { 16, "Aryan", "Dutt", "2002-06-16", "Male", "2021-07-10", 1, "aryan.dutt@college.edu",
                        "+91 9811002216", "Shivaji Nagar, Pune, Maharashtra", "Naveen Dutt" },
                { 17, "Palak", "Goel", "2002-09-29", "Female", "2021-07-10", 1, "palak.goel@college.edu",
                        "+91 9811002217", "Civil Lines, Agra, UP", "Ashok Goel" },
                { 18, "Samar", "Khanna", "2003-02-05", "Male", "2021-07-15", 1, "samar.khanna@college.edu",
                        "+91 9811002218", "Rajouri Garden, New Delhi", "Rohit Khanna" },
                { 19, "Tanya", "Sood", "2002-11-19", "Female", "2021-07-10", 1, "tanya.sood@college.edu",
                        "+91 9811002219", "The Mall, Shimla, HP", "Vijay Sood" },
                { 20, "Zafar", "Khan", "2003-01-01", "Male", "2021-08-01", 1, "zafar.khan@college.edu",
                        "+91 9811002220", "Bhopal MP, Near Lake View", "Imran Khan" }
        };

        for (Object[] row : studentRows) {
            Student s = new Student((Integer) row[0], (String) row[1], (String) row[2],
                    (String) row[3], (String) row[4], (String) row[5], (Integer) row[6]);
            s.setEmail((String) row[7]);
            s.setPhoneNumber((String) row[8]);
            s.setAddress((String) row[9]);
            s.setParentName((String) row[10]);
            s.setClassName("B.Tech CSE - 1st Year");
            IN_MEMORY_STUDENTS.put(s.getStudentId(), s);
        }

        // 5. Exams
        IN_MEMORY_EXAMS.put(1, new Exam(1, "Semester 1 Mid-Term 2025", "2025-10-15"));
        IN_MEMORY_EXAMS.put(2, new Exam(2, "Semester 1 Final 2026", "2026-03-01"));

        // 6. Attendance records from SQL
        String[] statusesDay1 = { "Present", "Present", "Absent", "Present", "Late", "Present", "Present", "Present",
                "Absent", "Present",
                "Present", "Late", "Present", "Present", "Absent", "Present", "Present", "Present", "Late", "Present" };
        for (int i = 0; i < 20; i++) {
            Attendance a = new Attendance(i + 1, i + 1, "2025-09-01", statusesDay1[i]);
            Student s = IN_MEMORY_STUDENTS.get(i + 1);
            if (s != null)
                a.setStudentName(s.getFullName());
            IN_MEMORY_ATTENDANCE.add(a);
        }

        // Attendance day 2 sample
        int[][] day2 = { { 1, 1 }, { 3, 1 }, { 5, 1 }, { 7, 0 }, { 9, 1 }, { 11, 2 }, { 13, 1 }, { 15, 1 }, { 17, 0 },
                { 19, 1 } };
        for (int j = 0; j < day2.length; j++) {
            int sid = day2[j][0];
            String st = day2[j][1] == 1 ? "Present" : (day2[j][1] == 2 ? "Late" : "Absent");
            Attendance a = new Attendance(21 + j, sid, "2025-09-02", st);
            Student s = IN_MEMORY_STUDENTS.get(sid);
            if (s != null)
                a.setStudentName(s.getFullName());
            IN_MEMORY_ATTENDANCE.add(a);
        }

        // 7. Results for all 20 students from SQL
        double[][] marksTable = {
                { 75.5, 88.0, 91.0, 82.0 }, // Student 1
                { 85.0, 79.5, 94.0, 90.0 }, // Student 2
                { 65.0, 70.0, 72.5, 68.0 }, // Student 3
                { 92.0, 95.0, 89.0, 91.5 }, // Student 4
                { 78.0, 82.0, 85.0, 80.0 }, // Student 5
                { 88.5, 91.0, 93.0, 87.0 }, // Student 6
                { 60.0, 65.5, 70.0, 62.0 }, // Student 7
                { 81.0, 84.0, 79.0, 85.5 }, // Student 8
                { 74.0, 78.0, 82.5, 76.0 }, // Student 9
                { 90.0, 88.0, 95.0, 92.0 }, // Student 10
                { 72.0, 68.0, 75.0, 70.0 }, // Student 11
                { 83.0, 87.5, 89.0, 84.0 }, // Student 12
                { 89.0, 92.0, 90.5, 91.0 }, // Student 13
                { 77.0, 81.0, 84.0, 79.5 }, // Student 14
                { 69.0, 73.0, 76.5, 71.0 }, // Student 15
                { 86.0, 89.0, 92.0, 88.0 }, // Student 16
                { 91.5, 94.0, 96.0, 93.5 }, // Student 17
                { 79.0, 83.0, 85.5, 81.0 }, // Student 18
                { 68.0, 72.0, 75.0, 70.0 }, // Student 19
                { 75.0, 80.0, 78.0, 85.0 } // Student 20
        };

        int rId = 1;
        for (int i = 0; i < 20; i++) {
            int sid = i + 1;
            Student stu = IN_MEMORY_STUDENTS.get(sid);
            String stuName = stu != null ? stu.getFullName() : "Student #" + sid;
            for (int sub = 1; sub <= 4; sub++) {
                Subject subject = IN_MEMORY_SUBJECTS.get(sub);
                ExamResult er = new ExamResult(rId++, sid, 1, sub, marksTable[i][sub - 1], 100);
                er.setStudentName(stuName);
                er.setExamName("Semester 1 Mid-Term 2025");
                er.setSubjectName(subject != null ? subject.getSubjectName() : "Subject #" + sub);
                IN_MEMORY_RESULTS.add(er);
            }
        }

        // 8. Default Users for Authentication
        IN_MEMORY_USERS.put("admin",
                new User(1, "admin", "admin123", "Admin", "Dean / System Administrator", "admin@college.edu"));
        IN_MEMORY_USERS.put("staff",
                new User(2, "staff", "staff123", "Staff", "Academic Registrar", "registrar@college.edu"));
        IN_MEMORY_USERS.put("student",
                new User(3, "student", "student123", "Student", "Rohan Gupta (Student)", "rohan.gupta@college.edu"));
    }
}
