<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-9">
            
            <!-- Action Toolbar (Hidden during print) -->
            <div class="d-flex justify-content-between align-items-center mb-4 no-print">
                <a href="${pageContext.request.contextPath}/results" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i> Back to Exam Standings
                </a>
                <div class="d-flex gap-2">
                    <a href="${pageContext.request.contextPath}/student/profile?id=${student.studentId}" class="btn btn-outline-primary">
                        <i class="bi bi-person me-1"></i> Student Profile
                    </a>
                    <button onclick="window.print()" class="btn btn-primary shadow-sm">
                        <i class="bi bi-printer-fill me-1"></i> Print Official Report Card
                    </button>
                </div>
            </div>

            <!-- Official Transcript Card -->
            <div class="official-slip shadow-sm">
                <div class="slip-watermark">ACADEMIC RECORD</div>

                <!-- Header -->
                <div class="slip-header text-center">
                    <div class="d-flex align-items-center justify-content-center gap-2 mb-1">
                        <span class="bg-primary text-white rounded p-2"><i class="bi bi-mortarboard-fill fs-3"></i></span>
                        <h3 class="fw-bold mb-0 text-dark">INSTITUTE OF HIGHER TECHNOLOGY</h3>
                    </div>
                    <div class="text-muted small">Department of Computer Science & Engineering &bull; Autonomous Institution</div>
                    <div class="badge bg-dark px-3 py-2 fs-7 mt-2 text-uppercase">Official Grade Card & Academic Transcript</div>
                </div>

                <!-- Student Information Grid -->
                <div class="row g-3 mb-4 p-3 bg-light rounded border">
                    <div class="col-sm-6">
                        <div class="small text-muted text-uppercase fw-bold">Student Name:</div>
                        <div class="fs-6 fw-bold text-dark">${reportCard.studentName}</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="small text-muted text-uppercase fw-bold">Registration / Roll No:</div>
                        <div class="fs-6 fw-bold text-primary">${student.registrationNumber}</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="small text-muted text-uppercase fw-bold">Program / Class:</div>
                        <div class="fw-semibold">${reportCard.className}</div>
                    </div>
                    <div class="col-sm-6">
                        <div class="small text-muted text-uppercase fw-bold">Examination:</div>
                        <div class="fw-semibold">${reportCard.examName}</div>
                    </div>
                </div>

                <!-- Subject-wise Marks Table -->
                <div class="table-responsive mb-4">
                    <table class="table table-bordered align-middle">
                        <thead class="table-light">
                            <tr>
                                <th class="text-center" style="width: 8%;">S.No</th>
                                <th>Subject Title</th>
                                <th class="text-center" style="width: 15%;">Max Marks</th>
                                <th class="text-center" style="width: 18%;">Marks Scored</th>
                                <th class="text-center" style="width: 15%;">Percentage</th>
                                <th class="text-center" style="width: 12%;">Grade</th>
                                <th class="text-center" style="width: 12%;">Result</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ##REPORT_CARD_SUBJECT_ROWS## -->
                        </tbody>
                        <tfoot class="table-light fw-bold">
                            <tr>
                                <td colspan="2" class="text-end">Aggregate Total:</td>
                                <td class="text-center">${reportCard.totalMaxMarks}</td>
                                <td class="text-center text-primary fs-6">${reportCard.totalMarksObtained}</td>
                                <td class="text-center text-success fs-6">${reportCard.overallPercentage}%</td>
                                <td colspan="2" class="text-center"><span class="badge bg-success">${reportCard.status}</span></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>

                <!-- Overall Performance Summary Box -->
                <div class="row g-3 p-3 border rounded bg-white mb-4">
                    <div class="col-sm-4 text-center border-end">
                        <div class="small text-muted text-uppercase fw-bold">Semester GPA Equivalent</div>
                        <div class="display-6 fw-bold text-primary">${reportCard.overallPercentage / 10.0}</div>
                        <div class="small text-muted">Out of 10.0 Scale</div>
                    </div>
                    <div class="col-sm-4 text-center border-end">
                        <div class="small text-muted text-uppercase fw-bold">Cumulative Grade</div>
                        <div class="display-6 fw-bold text-success">${reportCard.overallGrade}</div>
                        <div class="small text-muted">First Division with Distinction</div>
                    </div>
                    <div class="col-sm-4 text-center">
                        <div class="small text-muted text-uppercase fw-bold">Examination Result</div>
                        <div class="display-6 fw-bold text-success">PASSED</div>
                        <div class="small text-muted">Eligible for Next Semester</div>
                    </div>
                </div>

                <!-- Signatures & Official Validation -->
                <div class="pt-4 border-top d-flex justify-content-between align-items-end mt-4">
                    <div class="text-center">
                        <div class="border-bottom border-dark pb-1 mb-1" style="width: 140px; font-family: cursive;">
                            Priya Sharma
                        </div>
                        <div class="small text-muted">Class Teacher</div>
                    </div>

                    <div class="official-seal">
                        <i class="bi bi-shield-check fs-4 mb-1 text-primary"></i>
                        <span>Exam Cell</span>
                        <span style="font-size: 8px;">CONFIRMED</span>
                    </div>

                    <div class="text-center">
                        <div class="border-bottom border-dark pb-1 mb-1" style="width: 140px; font-family: cursive;">
                            Aarav Mehta
                        </div>
                        <div class="small text-muted">Controller of Examinations</div>
                    </div>
                </div>

            </div>

        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

