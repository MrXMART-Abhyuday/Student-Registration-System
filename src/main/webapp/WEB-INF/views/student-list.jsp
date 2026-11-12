<%@ include file="header.jsp" %>
<%@ include file="navbar.jsp" %>

<div class="container-fluid px-lg-4 py-4">
    <!-- Header -->
    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-1">Student Directory & Records</h3>
            <p class="text-muted small mb-0">Browse, filter, edit, or inspect official student dossiers.</p>
        </div>
        <div class="d-flex gap-2">
            <button onclick="window.print()" class="btn btn-outline-secondary">
                <i class="bi bi-printer me-1"></i> Print List
            </button>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-primary shadow-sm">
                <i class="bi bi-person-plus me-1"></i> Register Student
            </a>
        </div>
    </div>

    <!-- Alert notification if any -->
    <% if (request.getAttribute("infoMessage") != null && !((String)request.getAttribute("infoMessage")).isEmpty()) { %>
    <div class="alert alert-info alert-dismissible fade show d-flex align-items-center" role="alert">
        <i class="bi bi-info-circle-fill me-2 fs-5"></i>
        <div>${infoMessage}</div>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% } %>

    <!-- Search & Filter Card -->
    <div class="card mb-4 border-0 shadow-sm">
        <div class="card-body p-3">
            <form method="GET" action="${pageContext.request.contextPath}/students" class="row g-2 align-items-center">
                <div class="col-md-5">
                    <div class="input-group">
                        <span class="input-group-text bg-white border-end-0 text-muted"><i class="bi bi-search"></i></span>
                        <input type="text" class="form-control border-start-0" id="liveStudentSearch" name="q" value="${query}" placeholder="Search by name, roll no, email...">
                    </div>
                </div>

                <div class="col-md-3">
                    <select class="form-select" name="classId" onchange="this.form.submit()">
                        <option value="0">All Classes & Programs</option>
                        <!-- ##CLASS_SELECT_OPTIONS## -->
                    </select>
                </div>

                <div class="col-md-2">
                    <select class="form-select" name="gender" onchange="this.form.submit()">
                        <option value="All">All Genders</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                </div>

                <div class="col-md-2 d-flex gap-2">
                    <button type="submit" class="btn btn-primary w-100">Filter</button>
                    <a href="${pageContext.request.contextPath}/students" class="btn btn-light border" title="Reset Filters"><i class="bi bi-arrow-counterclockwise"></i></a>
                </div>
            </form>
        </div>
    </div>

    <!-- Students Table Card -->
    <div class="card shadow-sm border-0">
        <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center gap-2">
                <i class="bi bi-table text-primary"></i>
                <span class="fw-bold">Enrolled Students Roster</span>
            </div>
            <span class="badge bg-light text-dark border">Total Records: <strong class="text-primary">${students.size()}</strong></span>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th>Registration No.</th>
                        <th>Student Profile</th>
                        <th>Class / Program</th>
                        <th>Gender</th>
                        <th>Admission Date</th>
                        <th>Status</th>
                        <th class="text-end">Actions</th>
                    </tr>
                </thead>
                <tbody id="studentsTableBody">
                    <!-- ##STUDENTS_TABLE_ROWS## -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

