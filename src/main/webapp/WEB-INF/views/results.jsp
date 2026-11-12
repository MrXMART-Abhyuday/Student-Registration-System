<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container-fluid px-lg-4 py-4">
    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-1">Examinations & Grade Roster</h3>
            <p class="text-muted small mb-0">Record exam marks, review class GPA standings, and generate transcripts.</p>
        </div>
        <div class="d-flex gap-2">
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#enterMarksModal">
                <i class="bi bi-pencil-square me-1"></i> Enter / Update Marks
            </button>
            <button onclick="window.print()" class="btn btn-outline-secondary">
                <i class="bi bi-printer me-1"></i> Print Standings
            </button>
        </div>
    </div>

    <!-- Exam Selector Bar -->
    <div class="card border-0 shadow-sm mb-4">
        <div class="card-body p-3">
            <form method="GET" action="${pageContext.request.contextPath}/results" class="d-flex align-items-center gap-3">
                <label class="fw-semibold small text-muted mb-0"><i class="bi bi-mortarboard me-1"></i>Examination Cycle:</label>
                <select class="form-select" name="examId" onchange="this.form.submit()" style="max-width: 320px;">
                    <option value="1">Semester 1 Mid-Term 2025</option>
                    <option value="2">Semester 1 Final 2026</option>
                </select>
                <span class="badge bg-primary-subtle text-primary border border-primary-subtle px-3 py-2 ms-auto">
                    Focused on B.Tech CSE Class Curriculum (4 Core Subjects)
                </span>
            </form>
        </div>
    </div>

    <!-- Standings Table -->
    <div class="card border-0 shadow-sm">
        <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
            <span class="fw-bold"><i class="bi bi-trophy text-warning me-2"></i>Class Academic Performance & Semester Rankings</span>
            <span class="badge bg-light text-dark border">Total Evaluated: 20 Students</span>
        </div>
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th>Rank</th>
                        <th>Student Name</th>
                        <th>Class</th>
                        <th>Total Marks</th>
                        <th>Percentage</th>
                        <th>Grade</th>
                        <th class="text-end">Transcript</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- ##RESULTS_TABLE_ROWS## -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal: Enter Marks -->
<div class="modal fade" id="enterMarksModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="${pageContext.request.contextPath}/results">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold">Record Student Exam Marks</h5>
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
                        <label class="form-label">Exam</label>
                        <select class="form-select" name="examId">
                            <option value="1">Semester 1 Mid-Term 2025</option>
                            <option value="2">Semester 1 Final 2026</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Subject</label>
                        <select class="form-select" name="subjectId">
                            <!-- ##SUBJECT_SELECT_OPTIONS## -->
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Marks Obtained (Out of 100)</label>
                        <input type="number" step="0.5" min="0" max="100" class="form-control" name="marks" placeholder="e.g. 88.5" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Marks</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

