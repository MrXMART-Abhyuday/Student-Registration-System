<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container-fluid px-lg-4 py-4">
    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-1">Student Attendance Register</h3>
            <p class="text-muted small mb-0">Track and log daily classroom attendance for classes and subjects.</p>
        </div>
        <div>
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#markAttendanceModal">
                <i class="bi bi-plus-lg me-1"></i> Mark Attendance
            </button>
        </div>
    </div>

    <!-- Stats row -->
    <div class="row g-3 mb-4">
        <div class="col-sm-6 col-md-3">
            <div class="card p-3 border-0 shadow-sm d-flex flex-row align-items-center justify-content-between">
                <div>
                    <div class="text-muted small text-uppercase fw-bold">Present</div>
                    <div class="fs-3 fw-bold text-success">${stats.present}</div>
                </div>
                <div class="stat-icon bg-success-subtle text-success"><i class="bi bi-check-circle"></i></div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="card p-3 border-0 shadow-sm d-flex flex-row align-items-center justify-content-between">
                <div>
                    <div class="text-muted small text-uppercase fw-bold">Absent</div>
                    <div class="fs-3 fw-bold text-danger">${stats.absent}</div>
                </div>
                <div class="stat-icon bg-danger-subtle text-danger"><i class="bi bi-x-circle"></i></div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="card p-3 border-0 shadow-sm d-flex flex-row align-items-center justify-content-between">
                <div>
                    <div class="text-muted small text-uppercase fw-bold">Late</div>
                    <div class="fs-3 fw-bold text-warning">${stats.late}</div>
                </div>
                <div class="stat-icon bg-warning-subtle text-warning"><i class="bi bi-clock"></i></div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="card p-3 border-0 shadow-sm d-flex flex-row align-items-center justify-content-between">
                <div>
                    <div class="text-muted small text-uppercase fw-bold">Total Logs</div>
                    <div class="fs-3 fw-bold text-dark">${stats.total}</div>
                </div>
                <div class="stat-icon bg-primary-subtle text-primary"><i class="bi bi-list-check"></i></div>
            </div>
        </div>
    </div>

    <!-- Date Filter Bar -->
    <div class="card border-0 shadow-sm mb-4">
        <div class="card-body p-3">
            <form method="GET" action="${pageContext.request.contextPath}/attendance" class="d-flex align-items-center gap-3">
                <label class="fw-semibold small text-muted mb-0"><i class="bi bi-calendar3 me-1"></i>Select Attendance Date:</label>
                <input type="date" class="form-control" name="date" value="${selectedDate}" style="max-width: 200px;">
                <button type="submit" class="btn btn-outline-primary btn-sm">Load Date</button>
            </form>
        </div>
    </div>

    <!-- Attendance Table -->
    <div class="card border-0 shadow-sm">
        <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
            <span class="fw-bold"><i class="bi bi-calendar2-range text-primary me-2"></i>Attendance Records (${selectedDate})</span>
            <span class="badge bg-light text-dark border">Date: ${selectedDate}</span>
        </div>
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th>Entry ID</th>
                        <th>Student Name</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th class="text-end">Quick Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- ##ATTENDANCE_TABLE_ROWS## -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal: Mark Attendance -->
<div class="modal fade" id="markAttendanceModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="${pageContext.request.contextPath}/attendance">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold">Log Attendance</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Student</label>
                        <select class="form-select" name="studentId" required>
                            <!-- ##STUDENT_SELECT_OPTIONS## -->
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Date</label>
                        <input type="date" class="form-control" name="attendanceDate" value="${selectedDate}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Status</label>
                        <select class="form-select" name="status">
                            <option value="Present">Present</option>
                            <option value="Absent">Absent</option>
                            <option value="Late">Late</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Entry</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

