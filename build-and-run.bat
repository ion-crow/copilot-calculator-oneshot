@echo off
REM Java Calculator Build and Run Script for Windows

echo.
echo ======================================
echo   Java Calculator Build Script
echo ======================================
echo.

REM Check Java installation
echo Checking Java installation...
java --version
if %ERRORLEVEL% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

echo.
echo Checking Maven installation...
mvn --version
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven 3.6 or higher
    pause
    exit /b 1
)

echo.
echo ======================================
echo   Building Project
echo ======================================
echo.

REM Clean and compile
echo Cleaning project...
mvn clean
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven clean failed
    pause
    exit /b 1
)

echo.
echo Compiling project...
mvn compile
if %ERRORLEVEL% neq 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo Running tests...
mvn test
if %ERRORLEVEL% neq 0 (
    echo WARNING: Some tests failed
)

echo.
echo Packaging application...
mvn package
if %ERRORLEVEL% neq 0 (
    echo ERROR: Packaging failed
    pause
    exit /b 1
)

echo.
echo ======================================
echo   Build Complete!
echo ======================================
echo.
echo To run the calculator, use one of these commands:
echo.
echo 1. Using Maven (development):
echo    mvn javafx:run
echo.
echo 2. Using JAR file:
echo    java -jar target\java-calculator-1.0.0-shaded.jar
echo.
echo Press any key to run the calculator now...
pause > nul

echo.
echo Starting calculator...
mvn javafx:run
