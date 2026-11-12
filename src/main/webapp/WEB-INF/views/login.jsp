<%@ include file="header.jsp" %>

<div class="d-flex align-items-center justify-content-center min-vh-100 py-5" style="background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);">
    <div class="card shadow-lg border-0" style="max-width: 440px; width: 100%;">
        <div class="card-body p-4 p-sm-5">
            <!-- Header -->
            <div class="text-center mb-4">
                <span class="d-inline-flex align-items-center justify-content-center bg-primary text-white rounded-circle p-3 mb-3 shadow-sm" style="width: 58px; height: 58px;">
                    <i class="bi bi-mortarboard-fill fs-3"></i>
                </span>
                <h4 class="fw-bold text-dark mb-1">Student Registration System</h4>
                <p class="text-muted small">Sign in to your institutional administrative account</p>
            </div>

            <!-- Error message if login failed -->
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger d-flex align-items-center small py-2" role="alert">
                <i class="bi bi-exclamation-circle-fill me-2 fs-6"></i>
                <div>${errorMessage}</div>
            </div>
            <% } %>

            <!-- Login Form -->
            <form method="POST" action="${pageContext.request.contextPath}/login">
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <div class="input-group">
                        <span class="input-group-text bg-white text-muted"><i class="bi bi-person"></i></span>
                        <input type="text" class="form-control" name="username" value="admin" required autofocus>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <div class="input-group">
                        <span class="input-group-text bg-white text-muted"><i class="bi bi-lock"></i></span>
                        <input type="password" class="form-control" name="password" value="admin123" required>
                    </div>
                </div>

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="rememberMe" checked>
                        <label class="form-check-label small text-muted" for="rememberMe">Remember me</label>
                    </div>
                    <a href="${pageContext.request.contextPath}/register" class="small text-decoration-none">Self Register</a>
                </div>

                <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold shadow-sm">
                    <i class="bi bi-box-arrow-in-right me-1"></i> Sign In to Portal
                </button>
            </form>

            <!-- Quick Demo Credentials Hint -->
            <div class="mt-4 p-3 rounded bg-light border text-center small">
                <span class="fw-bold text-dark d-block mb-1"><i class="bi bi-key-fill text-warning me-1"></i>Quick Evaluator Credentials:</span>
                <div class="text-muted">Username: <strong class="text-dark">admin</strong> &bull; Password: <strong class="text-dark">admin123</strong></div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

