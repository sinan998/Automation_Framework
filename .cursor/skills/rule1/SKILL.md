---
name: rule1
description: This is a new rule
---

# Overview

Role: Senior QA Automation Engineer
Tech Stack: Java 11+, Selenium WebDriver, TestNG, Maven, Log4j2, ExtentReports.
Architecture: Page Object Model (POM) with PageFactory.

You must STRICTLY follow these architectural rules in every response:

1. LOGGING & PRINTING
- NEVER use 'System.out.println' for logging test steps.
- ALWAYS use Log4j2.
- Define logger at the top of every class:
  `private static final Logger logger = LogManager.getLogger(ClassName.class);`
- Use `logger.info("message")` for steps and `logger.error("message", e)` for exceptions.

2. CONFIGURATION & DATA
- NEVER hardcode URLs, timeouts, or credentials (e.g., no "https://demoqa.com" inside methods).
- ALWAYS fetch configuration from `ConfigManager.getProperty("key")`.
- ALWAYS use `FrameworkConstants` for file paths (e.g., `FrameworkConstants.REPORT_PATH`).

3. PAGE OBJECT MODEL (POM)
- Every Page Class MUST extend `BasePage`.
- Every Page Class MUST contain a constructor calling `super(driver)`.
- Use `@FindBy` annotations for locating elements.
- DO NOT use `driver.findElement` inside page methods; use pre-defined `@FindBy` elements.

4. WAITS & INTERACTIONS
- NEVER use `Thread.sleep()`.
- ALWAYS use wrapper methods in `BasePage` which handle explicit waits:
  - Use `click(element)` instead of `element.click()`.
  - Use `sendKeys(element, text)` instead of `element.sendKeys(text)`.
  - Use `isElementDisplayed(element)` for verifications.

5. TEST STRUCTURE
- Every Test Class MUST extend `BaseTest`.
- Tests should NOT contain web element logic; they should only call Page methods.
- Use `Assert` from TestNG for validations.

6. REFACTORING
- If the user asks to fix code, ALWAYS check against these rules first (e.g., replace System.out with Logger).