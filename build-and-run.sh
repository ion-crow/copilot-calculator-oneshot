#!/bin/bash

# Java Calculator Build and Run Script for Unix/Linux/macOS

echo
echo "======================================"
echo "   Java Calculator Build Script"
echo "======================================"
echo

# Check Java installation
echo "Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    exit 1
fi

java --version
if [ $? -ne 0 ]; then
    echo "ERROR: Java version check failed"
    exit 1
fi

echo
echo "Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

mvn --version
if [ $? -ne 0 ]; then
    echo "ERROR: Maven version check failed"
    exit 1
fi

echo
echo "======================================"
echo "   Building Project"
echo "======================================"
echo

# Clean and compile
echo "Cleaning project..."
mvn clean
if [ $? -ne 0 ]; then
    echo "ERROR: Maven clean failed"
    exit 1
fi

echo
echo "Compiling project..."
mvn compile
if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed"
    exit 1
fi

echo
echo "Running tests..."
mvn test
if [ $? -ne 0 ]; then
    echo "WARNING: Some tests failed"
fi

echo
echo "Packaging application..."
mvn package
if [ $? -ne 0 ]; then
    echo "ERROR: Packaging failed"
    exit 1
fi

echo
echo "======================================"
echo "   Build Complete!"
echo "======================================"
echo
echo "To run the calculator, use one of these commands:"
echo
echo "1. Using Maven (development):"
echo "   mvn javafx:run"
echo
echo "2. Using JAR file:"
echo "   java -jar target/java-calculator-1.0.0-shaded.jar"
echo
echo "Press any key to run the calculator now..."
read -n 1 -s

echo
echo "Starting calculator..."
mvn javafx:run
