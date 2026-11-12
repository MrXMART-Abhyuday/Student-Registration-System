@echo off
set "JAVA=java"
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
    set "JAVA=C:\Program Files\Java\jdk-21\bin\java.exe"
)

echo ========================================================
echo   Student Registration ^& College Management System
echo   One-Click Launcher (Java Servlets ^& JSP Server)
echo ========================================================

if not exist "bin\com\collegemanagement\server\AppServer.class" (
    echo [INFO] First time launch detected. Compiling project...
    call build.bat
    if errorlevel 1 (
        echo [ERROR] Build failed. Cannot launch server.
        pause
        exit /b 1
    )
)

echo [INFO] Starting Application Server on port 8080...
echo [INFO] Web URL: http://localhost:8080/
echo [INFO] Demo Credentials: admin / admin123
echo [INFO] Press Ctrl+C anytime to stop the server.
echo.
"%JAVA%" -cp "bin;lib\*" com.collegemanagement.server.AppServer

pause
