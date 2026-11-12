<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-9">
            <!-- Breadcrumb -->
            <nav aria-label="breadcrumb" class="mb-3">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/students">Students</a></li>
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/student/profile?id=${student.studentId}">${student.fullName}</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Edit Student</li>
                </ol>
            </nav>

            <div class="card shadow-sm border-0">
                <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="mb-0 fw-bold text-dark">Modify Student Information</h5>
                        <small class="text-muted">Update official records for <strong>${student.fullName}</strong> (${student.registrationNumber})</small>
                    </div>
                    <span class="badge bg-light text-dark border">ID: #${student.studentId}</span>
                </div>

                <div class="card-body p-4">
                    <form method="POST" action="${pageContext.request.contextPath}/student/edit">
                        <input type="hidden" name="studentId" value="${student.studentId}">

                        <!-- Section 1 -->
                        <div class="mb-4">
                            <h6 class="fw-bold text-primary text-uppercase fs-7 tracking-wider mb-3">1. Personal Information</h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label">First Name *</label>
                                    <input type="text" class="form-control" name="firstName" value="${student.firstName}" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Last Name *</label>
                                    <input type="text" class="form-control" name="lastName" value="${student.lastName}" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Date of Birth *</label>
                                    <input type="date" class="form-control" name="dateOfBirth" value="${student.dateOfBirth}" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Gender *</label>
                                    <select class="form-select" name="gender">
                                        <option value="Male" ${student.gender == 'Male' ? 'selected' : ''}>Male</option>
                                        <option value="Female" ${student.gender == 'Female' ? 'selected' : ''}>Female</option>
                                        <option value="Other" ${student.gender == 'Other' ? 'selected' : ''}>Other</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <hr class="my-4 text-muted opacity-25">

                        <!-- Section 2 -->
                        <div class="mb-4">
                            <h6 class="fw-bold text-primary text-uppercase fs-7 tracking-wider mb-3">2. Academic Placement</h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label">Class / Degree Program *</label>
                                    <select class="form-select" name="classId" required>
                                        <!-- ##CLASS_SELECT_OPTIONS## -->
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Admission Date *</label>
                                    <input type="date" class="form-control" name="admissionDate" value="${student.admissionDate}" required>
                                </div>
                            </div>
                        </div>

                        <hr class="my-4 text-muted opacity-25">

                        <!-- Section 3 -->
                        <div class="mb-4">
                            <h6 class="fw-bold text-primary text-uppercase fs-7 tracking-wider mb-3">3. Contact & Guardian Details</h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label">Email Address</label>
                                    <input type="email" class="form-control" name="email" value="${student.email}">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Phone Number</label>
                                    <input type="tel" class="form-control" name="phoneNumber" value="${student.phoneNumber}">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Parent / Guardian Name</label>
                                    <input type="text" class="form-control" name="parentName" value="${student.parentName}">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Permanent Address</label>
                                    <input type="text" class="form-control" name="address" value="${student.address}">
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-end gap-2 pt-3 border-top">
                            <a href="${pageContext.request.contextPath}/student/profile?id=${student.studentId}" class="btn btn-light border">Cancel</a>
                            <button type="submit" class="btn btn-primary px-4">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

