# QA Automation Portfolio

🌐 **English** | [Bahasa Indonesia](README.id.md)

[![Run Selenium Tests](https://github.com/mriaps/qa-automation-portfolio/actions/workflows/tests.yml/badge.svg)](https://github.com/mriaps/qa-automation-portfolio/actions/workflows/tests.yml)

A learning and practice portfolio for test automation using **Selenium WebDriver** and **Java**, built with the **Page Object Model (POM)** pattern to keep the code clean and easy to extend.

Every code change is verified automatically by **GitHub Actions (CI)**, so the whole test suite runs on every push and pull request.

## 🛠️ Tech Stack

| Tool | Purpose |
|---|---|
| Java 25 | Main programming language |
| Selenium WebDriver 4.52.0 | Browser automation |
| TestNG 7.12.0 | Testing framework and assertions, including data-driven testing with `@DataProvider` |
| Maven | Dependency management and build tool |
| Selenium Manager | Automatic browser driver management (built into Selenium, no manual setup) |
| GitHub Actions | Continuous Integration: runs the tests on every push and pull request |

## 📂 Project Structure

```
src/test/java/com/mriaps/
├── config/                 # Configuration reading
│   └── ConfigReader.java   # Reads config.properties (can be overridden with -Dkey=value)
├── drivers/                # WebDriver creation and configuration
│   └── DriverFactory.java  # Creates the Chrome driver (anti-popup options, headless mode on CI)
├── listeners/              # TestNG listeners
│   └── ScreenshotListener.java  # Takes a screenshot automatically when a test fails
├── pages/                  # Page Objects: locators and actions for each page
│   ├── LoginPage.java
│   ├── InventoryPage.java
│   ├── CartPage.java
│   └── CheckoutPage.java
├── tests/                  # Test cases and base setup
│   ├── BaseTest.java       # Test lifecycle: browser setup and teardown
│   ├── LoginTest.java      # Data-driven tests: successful and rejected logins
│   ├── CartTest.java       # Shopping cart test
│   └── CheckoutTest.java   # Cart validation, order summary, negative tests, and end-to-end checkout
└── utils/                  # Reusable helpers
    ├── PriceUtils.java     # Converts price text (e.g. "$29.99") to BigDecimal
    └── WaitHelper.java     # Centralized explicit waits, timeout read from config

src/test/resources/
└── config.properties       # Application URL, demo account, and wait timeout

.github/workflows/
└── tests.yml               # CI workflow: runs mvn test automatically
```

The project follows the **Page Object Model**: the logic for interacting with page elements is kept separate from the test logic (assertions), which makes the code easier to read, maintain, and extend with new pages or scenarios.

## ✅ Available Tests

Application under test: [SauceDemo](https://www.saucedemo.com)

| Test Case | Description |
|---|---|
| `shouldLoginSuccessfullyWithValidCredentials` | **Data-driven test**: verifies that login succeeds and the user lands on the inventory page for `standard_user` and `problem_user` |
| `shouldRejectInvalidLogin` | **Data-driven negative test** with 5 scenarios: locked-out user, wrong password, unregistered username, empty username, and empty password. Validates the error message and that the user stays on the login page |
| `shouldAddProductToCart` | Verifies that a product is added and the cart count increases |
| `shouldShowCorrectProductDetailsInCart` | Validates the cart contents: product names, quantities, and prices match what was added from the inventory page |
| `shouldCalculateOrderSummaryCorrectly` | Validates the order summary business logic: subtotal, tax (8%), and total |
| `shouldCompleteCheckoutSuccessfully` | End-to-end test: login → add product → fill checkout details → validate the overview (product and subtotal) → finish the order → verify the success message and the `checkout-complete` page |
| `shouldNotProceedWhenRequiredInformationIsMissing` | **Data-driven negative test**: verifies that checkout cannot continue when first name, last name, or zip code is empty, by validating the error message and the current page |

## 🚀 How to Run the Tests

**Prerequisites:**
- Java 25 (or the latest LTS version)
- Maven 3.9+
- Google Chrome installed

**Run all tests:**

```bash
mvn test
```

**Run a specific test class (example: `CheckoutTest`):**

```bash
mvn test -Dtest=CheckoutTest
```

**Override configuration from the command line (example: another environment):**

```bash
mvn test "-Dbase.url=https://staging.example.com"
```

The explicit wait timeout (10 seconds by default) can also be changed, for example when CI is slow:

```bash
mvn test "-Dtimeout.seconds=20"
```

Default values (application URL and demo account) live in `src/test/resources/config.properties`. A `-D` parameter always takes priority over the file.

**Screenshot on failure:** every failed test automatically gets a screenshot saved in `target/screenshots/`. The file name contains the class name, the test name, and the date and time, for example `CartTest_shouldAddProductToCart_2026-11-22_14-30-05-123.png`.

## ⚙️ Continuous Integration

Every push and pull request to the `main` branch automatically triggers GitHub Actions to:

1. Set up the environment (Java 25 and Google Chrome)
2. Run the whole test suite in headless mode
3. Report the result directly on the pull request

See the execution history in the [Actions](https://github.com/mriaps/qa-automation-portfolio/actions) tab.

## 🗺️ Roadmap

- [x] Maven project setup with Selenium and TestNG
- [x] Page Object Model
- [x] Login tests (positive and negative, data-driven)
- [x] Add-to-cart test
- [x] GitHub Actions CI
- [x] End-to-end checkout test
- [x] Stronger assertions: cart details, order summary (subtotal, tax, total), and negative checkout scenarios
- [x] Framework refactoring: `DriverFactory`, externalized configuration, and a centralized `WaitHelper`
- [x] Automatic screenshot on test failure
- [ ] Allure Report
- [ ] Upload the test report and screenshots as CI artifacts
- [ ] Maven dependency caching in CI
- [ ] Mobile automation with Appium (Android first)

## 👤 About Me

**Muhammad Rio Apriansah**

- 📧 Email: mrioapriansah22@gmail.com
- 💼 LinkedIn: [linkedin.com/in/mriaps](http://www.linkedin.com/in/mriaps)

This repository is part of my journey in building a QA Automation portfolio. I welcome feedback, discussion, and collaboration.
