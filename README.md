# SauceDemo Selenium Automation Testing Project

A basic test automation project built to practice web UI testing using Selenium WebDriver.

This project focuses on automating  functionalities of login and inventory pages in the  SauceDemo web application using a structured and maintainable approach.

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

### Inventory Page  Functionality
1. Verify page load with all products
2. Verify products load with low price to high price
3. Verify products load with high price to low price
4. Verify products names load in  Ascending  order
5. Verify products names load in  descending  order
6. Verify adding products update the cart badge


