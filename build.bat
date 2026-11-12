@echo off
set "JAVAC=javac"
set "JAVA=java"
set "JAR=jar"

if exist "C:\Program Files\Java\jdk-21\bin\javac.exe" (
    set "JAVAC=C:\Program Files\Java\jdk-21\bin\javac.exe"
    set "JAVA=C:\Program Files\Java\jdk-21\bin\java.exe"
    set "JAR=C:\Program Files\Java\jdk-21\bin\jar.exe"
)

echo ========================================================
echo   Student Registration ^& College Management System
echo   Build ^& Deployment Packaging Script
echo ========================================================

echo [1/4] Checking Java Development Kit...
"%JAVAC%" -version
if errorlevel 1 (
    echo [ERROR] JDK compiler not found. Please install JDK or set JAVA_HOME.
    exit /b 1
)

echo [2/4] Preparing build directories...
if not exist "bin" mkdir "bin"
if not exist "target" mkdir "target"
if not exist "lib" mkdir "lib"

echo [3/4] Compiling Java source files...
dir /s /b "src\main\java\*.java" > sources.txt
"%JAVAC%" -cp "lib/*;bin" -d "bin" -encoding UTF-8 @sources.txt
if errorlevel 1 (
    echo [ERROR] Compilation failed.
    del sources.txt 2>nul
    exit /b 1
)
del sources.txt 2>nul

if exist "src\main\resources" (
    xcopy /y /s /q "src\main\resources\*" "bin\" >nul 2>&1
)

echo [4/4] Packaging production WAR: target\student-registration-system.war...
if exist "target\war-staging" rd /s /q "target\war-staging"
mkdir "target\war-staging\WEB-INF\classes"

xcopy /y /s /q "src\main\webapp\*" "target\war-staging\" >nul 2>&1
xcopy /y /s /q "bin\com\*" "target\war-staging\WEB-INF\classes\com\" >nul 2>&1
if exist "src\main\resources\*" (
    xcopy /y /s /q "src\main\resources\*" "target\war-staging\WEB-INF\classes\" >nul 2>&1
)

"%JAR%" -cvf "target\student-registration-system.war" -C "target\war-staging" . >nul
rd /s /q "target\war-staging"

echo ========================================================
echo [SUCCESS] Build completed successfully!
echo   1. Standalone Classes: bin\
echo   2. Production Tomcat WAR: target\student-registration-system.war
echo ========================================================
