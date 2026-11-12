package com.collegemanagement.server;

import com.collegemanagement.controller.*;
import com.collegemanagement.filter.AuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class AppServer {
    private static final int PORT = 8080;
    private static final Map<String, StandardSession> SESSIONS = new ConcurrentHashMap<>();
    private static JspRenderer jspRenderer;
    private static File webappDir;

    private static final Map<String, HttpServlet> SERVLETS = new HashMap<>();
    private static final AuthenticationFilter AUTH_FILTER = new AuthenticationFilter();

    public static void main(String[] args) throws Exception {
        // Resolve webapp directory
        webappDir = new File("src/main/webapp");
        if (!webappDir.exists()) {
            webappDir = new File("College_database_management-main/src/main/webapp");
        }
        if (!webappDir.exists()) {
            webappDir = new File("../src/main/webapp");
        }
        jspRenderer = new JspRenderer(webappDir);

        // Register servlets
        DashboardServlet dashboardServlet = new DashboardServlet();
        StudentRegistrationServlet registrationServlet = new StudentRegistrationServlet();
        StudentListServlet listServlet = new StudentListServlet();
        StudentProfileServlet profileServlet = new StudentProfileServlet();
        StudentUpdateServlet updateServlet = new StudentUpdateServlet();
        StudentDeleteServlet deleteServlet = new StudentDeleteServlet();
        AttendanceServlet attendanceServlet = new AttendanceServlet();
        ResultsServlet resultsServlet = new ResultsServlet();
        ReportCardServlet reportCardServlet = new ReportCardServlet();
        AuthServlet authServlet = new AuthServlet();

        SERVLETS.put("/", dashboardServlet);
        SERVLETS.put("/dashboard", dashboardServlet);
        SERVLETS.put("/register", registrationServlet);
        SERVLETS.put("/students", listServlet);
        SERVLETS.put("/student/profile", profileServlet);
        SERVLETS.put("/student/edit", updateServlet);
        SERVLETS.put("/student/delete", deleteServlet);
        SERVLETS.put("/attendance", attendanceServlet);
        SERVLETS.put("/results", resultsServlet);
        SERVLETS.put("/report-card", reportCardServlet);
        SERVLETS.put("/login", authServlet);
        SERVLETS.put("/logout", authServlet);

        var executor = Executors.newVirtualThreadPerTaskExecutor();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("==================================================================");
            System.out.println("🎓 Student Registration & College Management System");
            System.out.println("🚀 Web Application Server started successfully!");
            System.out.println("🌐 Access URL: http://localhost:" + PORT + "/");
            System.out.println("📋 Database Status: " + (com.collegemanagement.dao.DBConnection.isMysqlConnected() ? "MySQL Active" : "In-Memory Store Active"));
            System.out.println("🔐 Demo Credentials: admin / admin123");
            System.out.println("==================================================================");

            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    executor.submit(() -> handleClient(socket));
                } catch (IOException e) {
                    if (serverSocket.isClosed()) break;
                }
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (socket;
             InputStream rawIn = socket.getInputStream();
             OutputStream rawOut = socket.getOutputStream()) {

            BufferedInputStream in = new BufferedInputStream(rawIn);
            
            // Read HTTP request line and headers
            String requestLine = readLine(in);
            if (requestLine == null || requestLine.isEmpty()) return;

            String[] reqParts = requestLine.split(" ");
            if (reqParts.length < 2) return;

            String method = reqParts[0];
            String fullUri = reqParts[1];

            Map<String, String> headers = new HashMap<>();
            String headerLine;
            int contentLength = 0;
            while ((headerLine = readLine(in)) != null && !headerLine.isEmpty()) {
                int colon = headerLine.indexOf(":");
                if (colon > 0) {
                    String hKey = headerLine.substring(0, colon).trim();
                    String hVal = headerLine.substring(colon + 1).trim();
                    headers.put(hKey.toLowerCase(), hVal);
                    if ("content-length".equalsIgnoreCase(hKey)) {
                        try { contentLength = Integer.parseInt(hVal); } catch (NumberFormatException ignored) {}
                    }
                }
            }

            // Read Body if present
            byte[] bodyBytes = new byte[contentLength];
            if (contentLength > 0) {
                int readTotal = 0;
                while (readTotal < contentLength) {
                    int r = in.read(bodyBytes, readTotal, contentLength - readTotal);
                    if (r < 0) break;
                    readTotal += r;
                }
            }

            // Parse URI
            String path = fullUri;
            String queryString = "";
            int qIdx = fullUri.indexOf("?");
            if (qIdx >= 0) {
                path = fullUri.substring(0, qIdx);
                queryString = fullUri.substring(qIdx + 1);
            }

            // Serve Static Assets
            if (path.startsWith("/assets/")) {
                serveStatic(rawOut, path);
                return;
            }

            // Match Servlet
            HttpServlet servlet = SERVLETS.get(path);
            if (servlet == null) {
                if (path.equals("/index.jsp") || path.equals("")) {
                    servlet = SERVLETS.get("/");
                }
            }

            if (servlet == null) {
                sendNotFound(rawOut, path);
                return;
            }

            // Execute Servlet via Filter Chain
            SocketHttpRequest req = new SocketHttpRequest(method, path, queryString, headers, bodyBytes);
            SocketHttpResponse resp = new SocketHttpResponse();

            final HttpServlet targetServlet = servlet;
            FilterChain chain = (filterReq, filterResp) -> {
                try {
                    targetServlet.service((HttpServletRequest) filterReq, (HttpServletResponse) filterResp);
                } catch (ServletException e) {
                    throw new IOException(e);
                }
            };

            AUTH_FILTER.doFilter(req, resp, chain);
            resp.writeTo(rawOut, req.getSessionId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readLine(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            if (b == '\n') break;
            if (b != '\r') baos.write(b);
        }
        if (b == -1 && baos.size() == 0) return null;
        return baos.toString(StandardCharsets.UTF_8);
    }

    private static void serveStatic(OutputStream out, String path) throws IOException {
        File file = new File(webappDir, path.startsWith("/") ? path.substring(1) : path);
        if (!file.exists() || file.isDirectory()) {
            sendNotFound(out, path);
            return;
        }

        String mime = "text/plain";
        if (path.endsWith(".css")) mime = "text/css; charset=UTF-8";
        else if (path.endsWith(".js")) mime = "application/javascript; charset=UTF-8";
        else if (path.endsWith(".svg")) mime = "image/svg+xml";
        else if (path.endsWith(".png")) mime = "image/png";
        else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) mime = "image/jpeg";
        else if (path.endsWith(".woff2")) mime = "font/woff2";

        byte[] bytes = Files.readAllBytes(file.toPath());
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        pw.print("HTTP/1.1 200 OK\r\n");
        pw.print("Content-Type: " + mime + "\r\n");
        pw.print("Content-Length: " + bytes.length + "\r\n");
        pw.print("Cache-Control: public, max-age=3600\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        out.write(bytes);
        out.flush();
    }

    private static void sendNotFound(OutputStream out, String path) throws IOException {
        byte[] bytes = ("<html><body><h1>404 Not Found</h1><p>The requested URL " + path + " was not found.</p></body></html>").getBytes(StandardCharsets.UTF_8);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        pw.print("HTTP/1.1 404 Not Found\r\n");
        pw.print("Content-Type: text/html; charset=UTF-8\r\n");
        pw.print("Content-Length: " + bytes.length + "\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        out.write(bytes);
        out.flush();
    }

    // ==========================================
    // HTTP Request Adapter
    // ==========================================
    private static class SocketHttpRequest implements HttpServletRequest {
        private final String method;
        private final String path;
        private final String queryString;
        private final Map<String, String> headers;
        private final Map<String, Object> attributes = new HashMap<>();
        private final Map<String, String[]> parameters = new HashMap<>();
        private StandardSession session;
        private String sessionId;
        private String characterEncoding = "UTF-8";

        public SocketHttpRequest(String method, String path, String queryString, Map<String, String> headers, byte[] bodyBytes) {
            this.method = method;
            this.path = path;
            this.queryString = queryString;
            this.headers = headers;

            if (queryString != null && !queryString.isEmpty()) {
                parseQueryString(queryString);
            }

            if ("POST".equalsIgnoreCase(method)) {
                String ct = headers.get("content-type");
                if (ct != null && ct.contains("application/x-www-form-urlencoded")) {
                    String body = new String(bodyBytes, StandardCharsets.UTF_8);
                    parseQueryString(body);
                }
            }

            String cookieHeader = headers.get("cookie");
            if (cookieHeader != null) {
                for (String c : cookieHeader.split(";")) {
                    String[] parts = c.trim().split("=");
                    if ("JSESSIONID".equalsIgnoreCase(parts[0]) && parts.length > 1) {
                        sessionId = parts[1];
                        session = SESSIONS.get(sessionId);
                    }
                }
            }
        }

        public String getSessionId() {
            return session != null ? session.getId() : null;
        }

        private void parseQueryString(String q) {
            String[] pairs = q.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                try {
                    String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                    String val = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : "";
                    parameters.compute(key, (k, existing) -> {
                        if (existing == null) return new String[]{val};
                        String[] next = Arrays.copyOf(existing, existing.length + 1);
                        next[existing.length] = val;
                        return next;
                    });
                } catch (Exception ignored) {}
            }
        }

        @Override public String getParameter(String name) { String[] vals = parameters.get(name); return (vals != null && vals.length > 0) ? vals[0] : null; }
        @Override public Enumeration<String> getParameterNames() { return Collections.enumeration(parameters.keySet()); }
        @Override public String[] getParameterValues(String name) { return parameters.get(name); }
        @Override public Map<String, String[]> getParameterMap() { return parameters; }
        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        @Override public void setAttribute(String name, Object o) { attributes.put(name, o); }
        @Override public void removeAttribute(String name) { attributes.remove(name); }
        @Override public String getCharacterEncoding() { return characterEncoding; }
        @Override public void setCharacterEncoding(String env) { this.characterEncoding = env; }
        @Override public int getContentLength() { return 0; }
        @Override public String getContentType() { return headers.get("content-type"); }
        @Override public String getProtocol() { return "HTTP/1.1"; }
        @Override public String getScheme() { return "http"; }
        @Override public String getServerName() { return "localhost"; }
        @Override public int getServerPort() { return PORT; }
        @Override public BufferedReader getReader() { return new BufferedReader(new StringReader("")); }
        @Override public String getRemoteAddr() { return "127.0.0.1"; }
        @Override public String getRemoteHost() { return "localhost"; }
        @Override public String getAuthType() { return null; }
        @Override public Cookie[] getCookies() { return new Cookie[0]; }
        @Override public long getDateHeader(String name) { return -1; }
        @Override public String getHeader(String name) { return headers.get(name.toLowerCase()); }
        @Override public Enumeration<String> getHeaders(String name) { return Collections.enumeration(Collections.emptyList()); }
        @Override public Enumeration<String> getHeaderNames() { return Collections.enumeration(headers.keySet()); }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getMethod() { return method; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getContextPath() { return ""; }
        @Override public String getQueryString() { return queryString; }
        @Override public String getRemoteUser() { return null; }
        @Override public String getRequestURI() { return path; }
        @Override public StringBuffer getRequestURL() { return new StringBuffer("http://localhost:" + PORT + path); }
        @Override public String getServletPath() { return path; }

        @Override
        public HttpSession getSession(boolean create) {
            if (session != null) return session;
            if (create) {
                sessionId = UUID.randomUUID().toString();
                session = new StandardSession(sessionId);
                SESSIONS.put(sessionId, session);
                return session;
            }
            return null;
        }

        @Override public HttpSession getSession() { return getSession(true); }
        @Override public boolean isRequestedSessionIdValid() { return session != null; }
        @Override public boolean isRequestedSessionIdFromCookie() { return true; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return new RequestDispatcher() {
                @Override
                public void forward(ServletRequest req, ServletResponse res) throws ServletException, IOException {
                    String html = jspRenderer.render(path, (HttpServletRequest) req);
                    res.setContentType("text/html; charset=UTF-8");
                    res.getWriter().write(html);
                }

                @Override
                public void include(ServletRequest req, ServletResponse res) throws ServletException, IOException {
                    forward(req, res);
                }
            };
        }

        @Override public ServletContext getServletContext() { return null; }
    }

    // ==========================================
    // HTTP Response Adapter
    // ==========================================
    private static class SocketHttpResponse implements HttpServletResponse {
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private final PrintWriter writer = new PrintWriter(new OutputStreamWriter(buffer, StandardCharsets.UTF_8));
        private int status = 200;
        private String contentType = "text/html; charset=UTF-8";
        private String redirectLocation = null;
        private final Map<String, String> headers = new HashMap<>();

        public void writeTo(OutputStream out, String newSessionId) throws IOException {
            writer.flush();
            byte[] bytes = buffer.toByteArray();

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
            if (redirectLocation != null) {
                pw.print("HTTP/1.1 302 Found\r\n");
                pw.print("Location: " + redirectLocation + "\r\n");
            } else {
                pw.print("HTTP/1.1 " + status + " OK\r\n");
                pw.print("Content-Type: " + contentType + "\r\n");
                pw.print("Content-Length: " + bytes.length + "\r\n");
            }

            if (newSessionId != null) {
                pw.print("Set-Cookie: JSESSIONID=" + newSessionId + "; Path=/; HttpOnly\r\n");
            }

            for (Map.Entry<String, String> e : headers.entrySet()) {
                pw.print(e.getKey() + ": " + e.getValue() + "\r\n");
            }

            pw.print("Connection: close\r\n\r\n");
            pw.flush();

            if (bytes.length > 0 && redirectLocation == null) {
                out.write(bytes);
                out.flush();
            }
        }

        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String name) { return headers.containsKey(name); }
        @Override public String encodeURL(String url) { return url; }
        @Override public String encodeRedirectURL(String url) { return url; }
        @Override public void sendError(int sc, String msg) { this.status = sc; }
        @Override public void sendError(int sc) { this.status = sc; }
        @Override public void sendRedirect(String location) { this.redirectLocation = location; this.status = 302; }
        @Override public void setDateHeader(String name, long date) {}
        @Override public void addDateHeader(String name, long date) {}
        @Override public void setHeader(String name, String value) { headers.put(name, value); }
        @Override public void addHeader(String name, String value) { headers.put(name, value); }
        @Override public void setIntHeader(String name, int value) { headers.put(name, String.valueOf(value)); }
        @Override public void addIntHeader(String name, int value) { headers.put(name, String.valueOf(value)); }
        @Override public void setStatus(int sc) { this.status = sc; }
        @Override public int getStatus() { return status; }
        @Override public String getHeader(String name) { return headers.get(name); }
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public String getContentType() { return contentType; }
        @Override public PrintWriter getWriter() { return writer; }
        @Override public void setCharacterEncoding(String charset) {}
        @Override public void setContentLength(int len) {}
        @Override public void setContentType(String type) { this.contentType = type; }
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return buffer.size(); }
        @Override public void flushBuffer() { writer.flush(); }
        @Override public void resetBuffer() { buffer.reset(); }
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() { buffer.reset(); }
    }

    // ==========================================
    // Session Implementation
    // ==========================================
    private static class StandardSession implements HttpSession {
        private final String id;
        private final long creationTime = System.currentTimeMillis();
        private long lastAccessedTime = System.currentTimeMillis();
        private final Map<String, Object> attributes = new ConcurrentHashMap<>();
        private int maxInactiveInterval = 1800;

        public StandardSession(String id) { this.id = id; }
        @Override public long getCreationTime() { return creationTime; }
        @Override public String getId() { return id; }
        @Override public long getLastAccessedTime() { return lastAccessedTime; }
        @Override public int getMaxInactiveInterval() { return maxInactiveInterval; }
        @Override public void setMaxInactiveInterval(int interval) { this.maxInactiveInterval = interval; }
        @Override public Object getAttribute(String name) { this.lastAccessedTime = System.currentTimeMillis(); return attributes.get(name); }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        @Override public void setAttribute(String name, Object value) { attributes.put(name, value); }
        @Override public void removeAttribute(String name) { attributes.remove(name); }
        @Override public void invalidate() { attributes.clear(); SESSIONS.remove(id); }
        @Override public boolean isNew() { return false; }
    }
}
