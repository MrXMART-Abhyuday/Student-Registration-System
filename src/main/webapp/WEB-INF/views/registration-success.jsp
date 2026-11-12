<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            
            <!-- Success Notification Bar -->
            <div class="alert alert-success d-flex align-items-center justify-content-between p-3 mb-4 shadow-sm" role="alert">
                <div class="d-flex align-items-center">
                    <i class="bi bi-check-circle-fill fs-3 text-success me-3"></i>
                    <div>
                        <h5 class="alert-heading mb-1 fw-bold">Admission Successfully Registered!</h5>
                        <div class="small">The student record has been committed to the institutional database.</div>
                    </div>
                </div>
                <div class="d-flex gap-2">
                    <button onclick="window.print()" class="btn btn-sm btn-outline-success bg-white">
                        <i class="bi bi-printer me-1"></i> Print Slip
                    </button>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-sm btn-success">
                        <i class="bi bi-plus-lg me-1"></i> New Admission
                    </a>
                </div>
            </div>

            <!-- Official Registration Slip -->
            <div class="official-slip shadow-sm">
                <div class="slip-watermark">ADMITTED</div>

                <!-- Slip Header -->
                <div class="slip-header d-flex justify-content-between align-items-start">
                    <div>
                        <div class="d-flex align-items-center gap-2 mb-1">
                            <span class="bg-primary text-white rounded p-1 d-inline-flex"><i class="bi bi-mortarboard-fill fs-4"></i></span>
                            <h4 class="fw-bold mb-0 text-dark">INSTITUTE OF HIGHER TECHNOLOGY</h4>
                        </div>
                        <div class="text-muted small">Office of the Academic Registrar & Admissions Council</div>
                        <div class="text-muted small">Campus Sector 12, Academic Block A &bull; Accredited Grade A+</div>
                    </div>
                    <div class="text-end">
                        <div class="badge bg-primary px-3 py-2 fs-7 mb-1">OFFICIAL ADMISSION SLIP</div>
                        <div class="small text-muted">Academic Year: 2025-2026</div>
                    </div>
                </div>

                <!-- Student Details Grid -->
                <div class="row g-4 mb-4">
                    <div class="col-sm-8">
                        <table class="table table-borderless table-sm">
                            <tbody>
                                <tr>
                                    <th class="text-muted small text-uppercase" style="width: 35%;">Registration No:</th>
                                    <td class="fw-bold text-primary fs-6">${student.registrationNumber}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Full Name:</th>
                                    <td class="fw-bold text-dark fs-6">${student.fullName}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Program / Class:</th>
                                    <td class="fw-semibold">${student.className}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Date of Birth:</th>
                                    <td>${student.dateOfBirth}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Gender:</th>
                                    <td>${student.gender}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Admission Date:</th>
                                    <td>${student.admissionDate}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Contact Email:</th>
                                    <td>${student.email}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Phone:</th>
                                    <td>${student.phoneNumber}</td>
                                </tr>
                                <tr>
                                    <th class="text-muted small text-uppercase">Guardian:</th>
                                    <td>${student.parentName}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="col-sm-4 d-flex flex-column align-items-center justify-content-between">
                        <!-- Photo placeholder -->
                        <div class="border rounded bg-light d-flex flex-column align-items-center justify-content-center p-3 text-center" style="width: 140px; height: 160px;">
                            <i class="bi bi-person-bounding-box fs-1 text-secondary mb-1"></i>
                            <span class="small text-muted" style="font-size: 11px;">Affix Student Photo</span>
                        </div>
                        <div class="mt-2 text-center">
                            <span class="badge bg-success-subtle text-success border border-success-subtle">
                                <i class="bi bi-check-circle me-1"></i> Status: ENROLLED
                            </span>
                        </div>
                    </div>
                </div>

                <!-- Footer Signatures & Verification -->
                <div class="pt-4 border-top d-flex justify-content-between align-items-end mt-4">
                    <div>
                        <div class="barcode-box mb-1">|||| | |||||| || ||||||| |||</div>
                        <div class="small text-muted" style="font-size: 11px;">Verification Token: ${student.registrationNumber}-AUTH</div>
                    </div>
                    <div class="official-seal">
                        <i class="bi bi-patch-check-fill fs-5 mb-1 text-primary"></i>
                        <span>Office of Registrar</span>
                        <span style="font-size: 8px;">VERIFIED</span>
                    </div>
                    <div class="text-center">
                        <div class="border-bottom border-dark pb-1 mb-1" style="width: 150px; font-family: cursive; font-size: 14px;">
                            Aarav Mehta
                        </div>
                        <div class="small text-muted" style="font-size: 11px;">Dean / Authorized Signatory</div>
                    </div>
                </div>
            </div>

            <!-- Page Action Buttons -->
            <div class="d-flex justify-content-between align-items-center mt-4 no-print">
                <a href="${pageContext.request.contextPath}/students" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i> Back to Student Directory
                </a>
                <div class="d-flex gap-2">
                    <a href="${pageContext.request.contextPath}/student/profile?id=${student.studentId}" class="btn btn-outline-primary">
                        <i class="bi bi-person-lines-fill me-1"></i> View Full Profile
                    </a>
                    <button onclick="window.print()" class="btn btn-primary">
                        <i class="bi bi-printer-fill me-1"></i> Print / Save Slip
                    </button>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

