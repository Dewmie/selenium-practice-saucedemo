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

├── base        (setup & teardown)

├── pages       (page objects)

└── tests       (test cases)

src/test/resources/

├── config.properties   (Browser & URL config)

├── login.csv           (Login test data)

└── checkout.csv        (Checkout test data)


## Test Cases Covered

1. Login - Valid/invalid login, empty fields, all user types 
2. Inventory - Product count, sorting (4 options), add to cart 
3. Cart - Product verification, remove item, checkout navigation 
4. Checkout - Form validation, order summary, confirmation 
5. End to End - Complete purchase journey 

## How to Run
1. Clone the repo
2. Set browser in `config.properties` → `browser=chrome`
3. Run via Maven: `mvn test`
4. View report: `reports/TestReport.html`



