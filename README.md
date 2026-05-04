# SauceDemo Selenium Automation Testing Project

A basic test automation project for [SauceDemo](https://www.saucedemo.com)
built with Java and Selenium WebDriver following Page Object Model.

## Tech Stack
`Java 21`
`Selenium WebDriver 4.41`
`TestNG`
`Maven`
`ExtentReports`

## Project Structure
`src/test/java/`

├── base        (setup & teardown)

├── pages       (page objects)

└── tests       (test cases)

`src/test/resources/`

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



