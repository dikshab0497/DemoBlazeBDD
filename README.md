🛒 DemoBlaze End-to-End Automation Framework

This project is a Selenium + Cucumber BDD automation framework for testing the DemoBlaze
 e-commerce website. It demonstrates end-to-end automation of critical account, product, and checkout flows, along with advanced Selenium scenarios, logging, reporting, and CI/CD integration.

📌 Project Highlights

✅ Selenium WebDriver for robust UI automation

📘 Cucumber BDD with human-readable Gherkin scenarios

🧪 TestNG for test execution and assertions

📊 Extent Reports for detailed HTML reports

📝 Log4j for runtime logging

📸 Automatic screenshots on test failures

🏗 Page Object Model (POM) for maintainable and reusable code

🔁 Hooks (Before/After) for test setup and teardown

🔄 CI/CD Integration using Jenkins/GitHub Actions

🔬 Advanced Selenium Scenarios: dropdowns, hover actions, scrolling, tables, keyboard actions, alerts/modals, drag & drop, window/tab switching

🛠️ Tech Stack
Tool	Purpose
Java	Programming Language
Selenium	UI Automation
Cucumber	Behavior-Driven Development
Maven	Dependency & Project Management
TestNG	Test Framework
Extent Reports	Advanced HTML Reporting
Log4j	Runtime Logging
Git & GitHub	Version Control
Jenkins / GitHub Actions	CI/CD Automation
WebDriverManager / ChromeDriver	Browser Driver
🗂️ Project Structure
DemoBlaze-Automation/
├── src/
│   ├── main/java/
│   │   └── pages/           # Page Object Classes
│   ├── test/java/
│   │   ├── stepDefinitions/ # Step Definitions for Cucumber
│   │   └── runner/          # Test Runner Classes
├── features/                # Gherkin Feature Files
├── test-output/             # Extent Reports & Screenshots
├── pom.xml                  # Maven Configuration
└── README.md                # Project Documentation

🧪 Test Coverage
1. Account Flow

Login with valid/invalid credentials

Sign up new users

Logout functionality

Alerts for duplicate registration

Field validations on account forms

2. Product Flow

Browse categories & search products

Add/remove products to/from cart

View product details & images

Pagination & dynamic scrolling

3. Checkout Flow

Fill checkout details & place orders

Handle alerts/modals for invalid data

Total price validation

4. Advanced Selenium Scenarios

Alerts / modal handling

Scrolling to elements

Keyboard actions (Enter, Tab, Delete)

Screenshot capture on failure

Drag & drop, window/tab switching

Dropdown selection & hover actions

Tables validation and sorting

Data-driven testing (Excel/JSON)

🚀 How to Run

Clone the repository

git clone https://github.com/dikshab0497/DemoBlaze-Automation.git
cd DemoBlaze-Automation


Import project into IntelliJ or Eclipse as a Maven project

Install dependencies

mvn clean install


Run tests

Using Cucumber Test Runner (.java class)

Or via Maven

mvn test


Reports

Extent Reports are generated automatically under /test-output/

Screenshots for failed steps are included in reports

📌 CI/CD Integration

Framework is ready for Jenkins or GitHub Actions pipelines

Automated test runs trigger reports and email notifications

Supports parallel execution for faster regression testing

🙋‍♀️ Author

Diksha Bandagale
📧 dikshabandagale0497@gmail.com

🔗 LinkedIn:https://www.linkedin.com/public-profile/settings/?trk=d_flagship3_profile_self_view_public_profile&lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_view_base%3BiD8MIP6fQXK5CjdCYjrBMQ%3D%3D

✅ This README now clearly shows:

Project scope and coverage

Tools and tech stack

Test flow and advanced features

CI/CD readiness

Easy-to-follow run instructions