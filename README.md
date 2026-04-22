# SauceDemo Selenium Automation Project

A basic test automation project built to practice web UI testing using Selenium WebDriver.

This project focuses on automating login functionality of the SauceDemo web application using a structured and maintainable approach

## Tech Stack
-Java 21

-Selenium WebDriver 4 

-TestNG

-WebDriverManager

-Maven

## Project Structure
src/test/java/

├── base/     → BaseTest (browser setup & teardown)

├── pages/    → Page Object classes (LoginPage)

└── tests/    → Test classes (LoginTest)


## Test Cases Covered
### Login Functionality
1. Verify successful login with valid credentials
2. Verify login failure with incorrect password
3. Verify login failure with invalid username
4. Verify login behavior with empty input fields

## How to Run
1. Clone this repository
2. Open in IntelliJ IDEA
3. Run LoginTest.java with TestNG
