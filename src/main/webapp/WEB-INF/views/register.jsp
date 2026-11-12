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
                    <li class="breadcrumb-item active" aria-current="page">Student Registration</li>
                </ol>
            </nav>

            <div class="card shadow-sm border-0">
                <div class="card-header bg-white py-3 border-bottom">
                    <div class="d-flex align-items-center gap-2">
                        <span class="d-inline-flex align-items-center justify-content-center bg-primary-subtle text-primary rounded p-2" style="width: 38px; height: 38px;">
                            <i class="bi bi-person-plus-fill fs-5"></i>
                        </span>
                        <div>
                            <h5 class="mb-0 fw-bold text-dark">New Student Admission & Registration</h5>
                            <small class="text-muted">Fill out the official student admission details below. Fields marked with * are mandatory.</small>
                        </div>
                    </div>
                </div>

                <div class="card-body p-4">
                    <!-- Error / Warning Messages -->
                    <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-danger d-flex align-items-center mb-4" role="alert">
                        <i class="bi bi-exclamation-triangle-fill fs-5 me-2"></i>
                        <div>${errorMessage}</div>
                    </div>
                    <% } %>

                    <form id="studentRegistrationForm" method="POST" action="${pageContext.request.contextPath}/register">
                        
                        <!-- Section 1: Personal Details -->
                        <div class="mb-4">
                            <h6 class="fw-bold text-primary text-uppercase fs-7 tracking-wider mb-3">
                                <i class="bi bi-person-vcard me-1"></i> 1. Personal Information
                            </h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="firstName" class="form-label">First Name <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="e.g. Rahul" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="lastName" class="form-label">Last Name <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="e.g. Sharma" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="dateOfBirth" class="form-label">Date of Birth <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="gender" class="form-label">Gender <span class="text-danger">*</span></label>
                                    <select class="form-select" id="gender" name="gender" required>
                                        <option value="Male" selected>Male</option>
                                        <option value="Female">Female</option>
                                        <option value="Other">Other</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <hr class="my-4 text-muted opacity-25">

                        <!-- Section 2: Academic Program -->
                        <div class="mb-4">
                            <h6 class="fw-bold text-primary text-uppercase fs-7 tracking-wider mb-3">
                                <i class="bi bi-mortarboard me-1"></i> 2. Academic Enrollment
                            </h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="classId" class="form-label">Assign Class / Degree Program <span class="text-danger">*</span></label>
                                    <select class="form-select" id="classId" name="classId" required>
                                        <!-- ##CLASS_SELECT_OPTIONS## -->
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label for="admissionDate" class="form-label">Admission Date</label>
                                    <input type="date" class="form-control" id="admissionDate" name="admissionDate" value="${todayDate}">
                                </div>
                            </div>
                        </div>

                        <hr class="my-4 text-muted opacity-25">

                        <!-- Section 3: Contact & Emergency Info -->
                        <div class="mb-4">
                            <h6 class="fw-bold text-primary text-uppercase fs-7 tracking-wider mb-3">
                                <i class="bi bi-geo-alt me-1"></i> 3. Contact & Guardian Details
                            </h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="email" class="form-label">Student Email Address</label>
                                    <input type="email" class="form-control" id="email" name="email" placeholder="e.g. rahul.sharma@college.edu">
                                </div>
                                <div class="col-md-6">
                                    <label for="phoneNumber" class="form-label">Contact Phone Number</label>
                                    <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="e.g. +91 9811002201">
                                </div>
                                <div class="col-md-6">
                                    <label for="parentName" class="form-label">Parent / Guardian Full Name</label>
                                    <input type="text" class="form-control" id="parentName" name="parentName" placeholder="e.g. Suresh Sharma">
                                </div>
                                <div class="col-md-6">
                                    <label for="address" class="form-label">Permanent Residential Address</label>
                                    <input type="text" class="form-control" id="address" name="address" placeholder="e.g. 142 Lake View Road, New Delhi">
                                </div>
                            </div>
                        </div>

                        <!-- Form Submission Controls -->
                        <div class="d-flex justify-content-end gap-2 pt-3 border-top">
                            <a href="${pageContext.request.contextPath}/students" class="btn btn-light border">Cancel</a>
                            <button type="reset" class="btn btn-outline-secondary">Reset Form</button>
                            <button type="submit" class="btn btn-primary px-4">
                                <i class="bi bi-check2-circle me-1"></i> Complete Registration
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

