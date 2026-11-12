<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-custom sticky-top">
    <div class="container-fluid px-lg-4">
        <a class="navbar-brand d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/dashboard">
            <span class="d-inline-flex align-items-center justify-content-center bg-primary text-white rounded p-2" style="width: 36px; height: 36px;">
                <i class="bi bi-mortarboard-fill fs-5"></i>
            </span>
            <span>EduReg <span class="badge bg-primary-subtle text-primary fw-semibold fs-7 ms-1">v2.0</span></span>
        </a>

        <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#topNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="topNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-3 gap-1">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                        <i class="bi bi-speedometer2 me-1"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/register">
                        <i class="bi bi-person-plus-fill me-1 text-primary"></i> Register Student
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/students">
                        <i class="bi bi-people-fill me-1"></i> Student Directory
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/attendance">
                        <i class="bi bi-calendar-check me-1"></i> Attendance
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/results">
                        <i class="bi bi-award-fill me-1"></i> Exams & Marks
                    </a>
                </li>
            </ul>

            <div class="d-flex align-items-center gap-3">
                <span class="badge bg-success-subtle text-success border border-success-subtle px-2 py-1 small">
                    <i class="bi bi-database-check me-1"></i> SchoolManagementDB
                </span>

                <div class="dropdown">
                    <button class="btn btn-sm btn-light border dropdown-toggle d-flex align-items-center gap-2" type="button" data-bs-toggle="dropdown">
                        <div class="avatar-xs rounded-circle bg-dark text-white d-flex align-items-center justify-content-center" style="width:26px; height:26px; font-size:12px;">
                            <i class="bi bi-person-fill"></i>
                        </div>
                        <span class="fw-semibold small">${currentUser.fullName}</span>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end shadow-sm border-0">
                        <li><h6 class="dropdown-header">Role: ${currentUser.role}</h6></li>
                        <li><a class="dropdown-item small" href="${pageContext.request.contextPath}/students"><i class="bi bi-gear me-2"></i>Manage Students</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item text-danger small" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Sign Out</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

