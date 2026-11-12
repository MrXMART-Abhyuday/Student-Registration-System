<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container-fluid px-lg-4 py-4">
    <!-- Welcome Header -->
    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-1">Academic Administration Dashboard</h3>
            <p class="text-muted small mb-0">Overview of student registrations, enrollment stats, attendance, and exam performance.</p>
        </div>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/register" class="btn btn-primary shadow-sm">
                <i class="bi bi-person-plus me-1"></i> Register New Student
            </a>
            <a href="${pageContext.request.contextPath}/students" class="btn btn-outline-secondary">
                <i class="bi bi-person-lines-fill me-1"></i> View Directory
            </a>
        </div>
    </div>

    <!-- KPI Metric Cards -->
    <div class="row g-3 mb-4">
        <div class="col-sm-6 col-xl-3">
            <div class="stat-card">
                <div>
                    <div class="stat-title">Total Registered Students</div>
                    <div class="stat-value text-primary">${totalStudents}</div>
                    <div class="small text-success mt-1"><i class="bi bi-arrow-up"></i> 100% Enrolled</div>
                </div>
                <div class="stat-icon bg-primary-subtle text-primary">
                    <i class="bi bi-people-fill"></i>
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-xl-3">
            <div class="stat-card">
                <div>
                    <div class="stat-title">Active Classes & Batches</div>
                    <div class="stat-value text-info">${totalClasses}</div>
                    <div class="small text-muted mt-1"><i class="bi bi-diagram-3"></i> B.Tech CSE / IT</div>
                </div>
                <div class="stat-icon bg-info-subtle text-info">
                    <i class="bi bi-journal-bookmark-fill"></i>
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-xl-3">
            <div class="stat-card">
                <div>
                    <div class="stat-title">Faculty Members</div>
                    <div class="stat-value text-warning">${totalTeachers}</div>
                    <div class="small text-muted mt-1"><i class="bi bi-person-badge"></i> Assigned Faculty</div>
                </div>
                <div class="stat-icon bg-warning-subtle text-warning">
                    <i class="bi bi-mortarboard-fill"></i>
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-xl-3">
            <div class="stat-card">
                <div>
                    <div class="stat-title">Overall Attendance</div>
                    <div class="stat-value text-success">${attendanceRate}%</div>
                    <div class="small text-success mt-1"><i class="bi bi-check2-all"></i> Healthy Average</div>
                </div>
                <div class="stat-icon bg-success-subtle text-success">
                    <i class="bi bi-calendar-check-fill"></i>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Content Area -->
    <div class="row g-4">
        <!-- Recent Registrations -->
        <div class="col-lg-7">
            <div class="card h-100">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-clock-history text-primary"></i>
                        <span>Recent Student Registrations</span>
                    </div>
                    <a href="${pageContext.request.contextPath}/students" class="btn btn-sm btn-light border">View All</a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead>
                            <tr>
                                <th>Roll No.</th>
                                <th>Student Name</th>
                                <th>Class</th>
                                <th>Admission Date</th>
                                <th class="text-end">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ##RECENT_STUDENTS_ROWS## -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Academic Top Performers -->
        <div class="col-lg-5">
            <div class="card h-100">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-trophy-fill text-warning"></i>
                        <span>Semester 1 Mid-Term Top Performers</span>
                    </div>
                    <a href="${pageContext.request.contextPath}/results" class="btn btn-sm btn-light border">All Marks</a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead>
                            <tr>
                                <th>Rank</th>
                                <th>Student</th>
                                <th>Percentage</th>
                                <th>Grade</th>
                                <th class="text-end">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ##TOP_PERFORMERS_ROWS## -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

