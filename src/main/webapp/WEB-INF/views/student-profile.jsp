<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container py-4">
    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="mb-3">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/students">Students</a></li>
            <li class="breadcrumb-item active" aria-current="page">${student.fullName}</li>
        </ol>
    </nav>

    <!-- Student Dossier Header Card -->
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4">
            <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3">
                <div class="d-flex align-items-center gap-3">
                    <div class="rounded-circle bg-primary bg-gradient text-white d-flex align-items-center justify-content-center shadow-sm" style="width: 72px; height: 72px; font-size: 28px; font-weight: 700;">
                        ${student.firstName.substring(0,1)}
                    </div>
                    <div>
                        <div class="d-flex align-items-center gap-2 mb-1">
                            <h3 class="fw-bold text-dark mb-0">${student.fullName}</h3>
                            <span class="badge bg-success-subtle text-success border border-success-subtle">Active</span>
                        </div>
                        <div class="d-flex flex-wrap align-items-center gap-3 text-muted small">
                            <span><i class="bi bi-person-badge me-1 text-primary"></i> ${student.registrationNumber}</span>
                            <span>&bull;</span>
                            <span><i class="bi bi-mortarboard me-1 text-info"></i> ${student.className}</span>
                            <span>&bull;</span>
                            <span><i class="bi bi-calendar-event me-1"></i> Admitted: ${student.admissionDate}</span>
                        </div>
                    </div>
                </div>

                <div class="d-flex flex-wrap gap-2">
                    <a href="${pageContext.request.contextPath}/report-card?id=${student.studentId}" class="btn btn-outline-info">
                        <i class="bi bi-file-earmark-text me-1"></i> Official Transcript
                    </a>
                    <a href="${pageContext.request.contextPath}/student/edit?id=${student.studentId}" class="btn btn-outline-secondary">
                        <i class="bi bi-pencil me-1"></i> Edit Profile
                    </a>
                    <button onclick="window.print()" class="btn btn-light border">
                        <i class="bi bi-printer"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <!-- Personal & Contact Details -->
        <div class="col-lg-4">
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-white py-3">
                    <span class="fw-bold"><i class="bi bi-person-vcard text-primary me-2"></i>Personal & Contact Info</span>
                </div>
                <div class="card-body p-3">
                    <ul class="list-group list-group-flush small">
                        <li class="list-group-item d-flex justify-content-between py-2 px-0">
                            <span class="text-muted">Date of Birth</span>
                            <span class="fw-semibold">${student.dateOfBirth}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between py-2 px-0">
                            <span class="text-muted">Gender</span>
                            <span class="fw-semibold">${student.gender}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between py-2 px-0">
                            <span class="text-muted">Email</span>
                            <span class="fw-semibold text-primary">${student.email}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between py-2 px-0">
                            <span class="text-muted">Phone</span>
                            <span class="fw-semibold">${student.phoneNumber}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between py-2 px-0">
                            <span class="text-muted">Parent / Guardian</span>
                            <span class="fw-semibold">${student.parentName}</span>
                        </li>
                        <li class="list-group-item d-flex flex-column py-2 px-0">
                            <span class="text-muted mb-1">Residential Address</span>
                            <span class="fw-semibold">${student.address}</span>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- Attendance Rate KPI -->
            <div class="card shadow-sm border-0">
                <div class="card-header bg-white py-3">
                    <span class="fw-bold"><i class="bi bi-calendar-check text-success me-2"></i>Attendance Metric</span>
                </div>
                <div class="card-body p-4 text-center">
                    <div class="display-5 fw-bold text-success mb-1">${attendancePercentage}%</div>
                    <div class="text-muted small mb-3">Overall Class Attendance</div>
                    <div class="progress" style="height: 10px;">
                        <div class="progress-bar bg-success" role="progressbar" style="width: ${attendancePercentage}%;"></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Academic Curriculum & Exam Results -->
        <div class="col-lg-8">
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                    <span class="fw-bold"><i class="bi bi-award text-warning me-2"></i>Semester Exam Performance</span>
                    <a href="${pageContext.request.contextPath}/report-card?id=${student.studentId}" class="btn btn-sm btn-outline-primary">
                        Generate Report Card
                    </a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead>
                            <tr>
                                <th>Subject</th>
                                <th>Marks Obtained</th>
                                <th>Percentage</th>
                                <th>Grade</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ##STUDENT_PROFILE_SUBJECTS## -->
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Attendance History Summary -->
            <div class="card shadow-sm border-0">
                <div class="card-header bg-white py-3">
                    <span class="fw-bold"><i class="bi bi-clock-history text-info me-2"></i>Recent Attendance Log</span>
                </div>
                <div class="card-body p-3">
                    <div class="d-flex flex-wrap gap-2">
                        <span class="badge bg-success-subtle text-success border border-success-subtle p-2">
                            <i class="bi bi-check-circle me-1"></i> Present on All Standard Session Days
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

