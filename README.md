# 🛒 DemoBlaze — Selenium Cucumber BDD Automation Framework

A production-ready end-to-end test automation framework for the [DemoBlaze](https://www.demoblaze.com/) e-commerce application, built with **Selenium WebDriver**, **Java**, **Cucumber BDD**, **TestNG**, and **Maven** — with full CI/CD integration via Jenkins.

---

## 📌 Project Highlights

| | |
|--|--|
| ✅ | Selenium WebDriver for robust UI automation |
| 📘 | Cucumber BDD with human-readable Gherkin scenarios |
| 🧪 | TestNG for test execution and assertions |
| 📊 | Extent Reports for detailed HTML test reports |
| 📝 | Log4j2 for console and rolling file logging |
| 📸 | Automatic screenshots on pass and fail |
| 🏗 | Page Object Model (POM) for maintainable, reusable code |
| 🔁 | Cucumber Hooks for test setup and teardown |
| 🔄 | CI/CD integration via Jenkins pipeline |
| 🔬 | Advanced Selenium: dropdowns, hover, scroll, drag & drop, windows, tables, keyboard actions |
| 🔁 | Retry Analyzer — auto-retries flaky tests up to 2 times |
| 📂 | Data-driven testing via Excel (Apache POI) |
| 🧵 | ThreadLocal WebDriver for parallel execution safety |

---

## 🛠️ Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 21 | Core programming language |
| Selenium WebDriver | 4.33.0 | Browser automation |
| Cucumber | 7.14.0 | BDD framework |
| TestNG | 7.10.1 | Test execution & assertions |
| Maven | 3.x | Build & dependency management |
| Log4j2 | 2.23.1 | Console & rolling file logging |
| ExtentReports | 5.1.1 | Rich HTML test reports |
| Apache POI | 5.2.5 | Excel data handling |
| Jenkins | Latest | CI/CD pipeline |
| Git & GitHub | - | Version control |

---

## 🗂️ Project Structure

```
DemoBlazeBDD/
├── src/
│   └── test/
│       ├── java/
│       │   ├── hooks/
│       │   │   └── Hooks.java                   # Cucumber @Before/@After lifecycle
│       │   ├── pages/
│       │   │   ├── BasePage.java                # Page base class with logger
│       │   │   ├── HomePage.java
│       │   │   ├── LoginPage.java
│       │   │   ├── SignupPage.java
│       │   │   ├── CartPage.java
│       │   │   ├── LaptopProductsPage.java
│       │   │   ├── PlaceOrderPage.java
│       │   │   └── ConfirmationPage.java
│       │   ├── runner/
│       │   │   └── TestNGRunner.java            # Cucumber TestNG runner
│       │   ├── StepDefinitions/
│       │   │   ├── HomeStepDefinition.java
│       │   │   ├── LoginStepDefinition.java
│       │   │   ├── SignupStepDefinition.java
│       │   │   ├── CartStepDefinition.java
│       │   │   ├── ProductStepDefinition.java
│       │   │   ├── PlaceOrderStepDefinition.java
│       │   │   ├── ConfirmationStepDefinition.java
│       │   │   ├── DropdownStepDefintion.java
│       │   │   ├── DraganddropStepDefinition.java
│       │   │   ├── HoverActionStepDefinition.java
│       │   │   ├── TableValidationStepDefinition.java
│       │   │   └── WindowSwitchStepDefinition.java
│       │   ├── testBase/
│       │   │   └── BaseClass.java               # ThreadLocal WebDriver, logger
│       │   └── utilities/
│       │       ├── ConfigPropertiesUtility.java
│       │       ├── ExcelUtility.java
│       │       ├── ExtentReportManager.java
│       │       ├── ScreenshotUtility.java
│       │       ├── PageObjectManager.java
│       │       ├── RetryAnalyzer.java
│       │       ├── RetryListener.java
│       │       ├── ScenarioContextGlobalDataUtility.java
│       │       ├── ChromeProfileManagerUtility.java
│       │       └── TestContext.java
│       └── resources/
│           ├── features/
│           │   ├── DemoBlaze.feature
│           │   └── SeleniumPlayground.feature
│           ├── testdata/
│           │   └── ProductNames.xlsx
│           ├── config.properties
│           └── log4j2.xml
├── logs/
│   └── automation.log
├── ExtentReport/
├── Jenkinsfile
├── master.xml
└── pom.xml
```

---

## 🧪 Test Coverage

### 1. Account Flow
- Login with valid and invalid credentials
- Sign up new users
- Logout functionality
- Alerts for duplicate registration
- Field validations on account forms
- Login via keyboard actions (Tab, Enter)

### 2. Product Flow
- Browse categories and search products
- Add and remove products from cart
- View product details, price, and description
- Pagination and dynamic scrolling
- Scroll to specific product element

### 3. Checkout Flow
- Fill checkout details and place orders
- Handle alerts and modals for invalid/missing data
- Total price calculation and validation (Excel driven)
- Confirmation screen validation

### 4. Advanced Selenium Scenarios
- Alerts and modal handling
- Drag and drop functionality
- Dropdown selection and validation
- Hover actions and sub-menu display
- Table sorting and data validation
- New window/tab switching
- Screenshot capture on failure

---

## ⚙️ Prerequisites

- **Java JDK 21** — [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.x** — [Download](https://maven.apache.org/download.cgi)
- **Google Chrome** or **Microsoft Edge** (latest)
- **Git** — [Download](https://git-scm.com/)
- **Eclipse / IntelliJ IDEA** (for local development)

---

## 🚀 Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/dikshab0497/DemoBlazeBDD.git
cd DemoBlazeBDD
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Configure `config.properties`

Located at `src/test/resources/config.properties`:

```properties
# Browser
browser=chrome

# Execution Environment
execution_env=local

# Application URLs
qa.appURL=https://www.demoblaze.com/
uat.appURL=https://www.demoblaze.com/

# Test Credentials
qa.userName=your_username
qa.password=your_password

# Default Test Tag
testCase=@Smoke

# External Tool URLs
dropdown_url=https://the-internet.herokuapp.com/dropdown
hover_url=https://the-internet.herokuapp.com/hovers
dragdrop_url=https://the-internet.herokuapp.com/drag_and_drop
window_url=https://the-internet.herokuapp.com/windows
```

> ⚠️ **Never commit `config.properties` with real credentials to GitHub.** Add it to `.gitignore`.

---

## ▶️ Running Tests

### Run via Eclipse
1. Right-click `TestNGRunner.java` → **Run As** → **TestNG Test**
2. Or right-click `testng.xml` → **Run As** → **TestNG Suite**

### Run via Maven

```bash
# Run default tag from config.properties
mvn test

# Run specific tag
mvn test -Dcucumber.filter.tags=@Smoke

# Run on specific environment and browser
mvn test -Dcucumber.filter.tags=@LogOut -Denv=qa -Dbrowser=chrome

# Run on UAT environment
mvn test -Dcucumber.filter.tags=@Smoke -Denv=uat -Dbrowser=edge
```

---

## 🏷️ Available Test Tags

### DemoBlaze Feature

| Tag | Description |
|-----|-------------|
| `@Smoke` | Core smoke tests |
| `@Regression` | Full regression suite |
| `@LoginWithValidCred` | Login with valid credentials |
| `@LoginWithInValidCred` | Login with invalid credentials |
| `@SignUp` | Sign up new user |
| `@LogOut` | Logout functionality |
| `@SignUpWithExistingCred` | Sign up with existing credentials |
| `@FieldValidationOnSignUp` | Field validation on sign up |
| `@BrowseCategort` | Browse product category |
| `@SearchProduct` | Search product from category |
| `@AddToCart` | Add product to cart |
| `@DeleteProductFromCart` | Delete product from cart |
| `@ValidateProductDetails` | Validate product details |
| `@ScrollingNavigation` | Scroll and fetch all products |
| `@PlaceOrder` | Place order end to end |
| `@PlaceOrderWithMissingData` | Place order with missing data |
| `@MultipleProductSelection` | Add multiple products from Excel |
| `@ScrollTillParticularElement` | Scroll to specific product |
| `@KeyboardAction` | Login using keyboard actions |

### Selenium Playground Feature

| Tag | Description |
|-----|-------------|
| `@DropdownSelection` | Dropdown selection validation |
| `@HoverAction` | Hover action validation |
| `@DragAndDrop` | Drag and drop functionality |
| `@WindowSwitch` | New window switch validation |
| `@TableValidation` | Table sorting validation |

---

## 📊 Reports & Logs

### Extent Report
Generated automatically after each run:
```
ExtentReport/<TagName>_<timestamp>.html
```
Open the HTML file in any browser to view detailed results with screenshots.

### Log File
```
logs/automation.log
```
Step-by-step execution logs with timestamps, thread info, and log levels (INFO/WARN/ERROR).

---

## 🔁 Retry Mechanism

Failed tests are automatically retried **2 times** before being marked as failed — handles intermittent network or timing issues without manual re-runs.

---

## 🤖 Jenkins CI/CD Pipeline

### Pipeline Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `TestCase` | String | `@LogOut` | Cucumber tag to execute |
| `Environment` | Choice | `QA` | Target environment (QA / UAT / PRODUCTION) |
| `Browser` | Choice | `chrome` | Browser (chrome / edge) |
| `Branch` | String | `main` | Git branch to checkout |

### Pipeline Stages

```
Checkout Code → Build (mvn compile) → Run Tests → Publish Report → Email Notification
```

### Jenkins Setup

1. Install plugins: **Git**, **Maven Integration**, **HTML Publisher**, **Email Extension**
2. Configure Maven tool named `M3` under **Global Tool Configuration**
3. Create a new **Pipeline** job and point to this repository
4. Configure SMTP email settings under **Manage Jenkins → Configure System**

### Run Condition
Tests execute only when:
- `Environment` = `QA`
- `Browser` = `chrome`
- `TestCase` tag is not empty

### Post Build
- ✅ **ExtentReport** published to Jenkins build sidebar
- ✅ **Email notification** sent after every build with build status, environment, browser, tag, and report link

---

## 🧱 Framework Architecture

```
┌─────────────────────────────────────────────────┐
│             Feature Files (.feature)             │
│         (Gherkin BDD — Given/When/Then)         │
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│              Step Definitions                    │
│      (Maps Gherkin steps to Java methods)       │
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│               Page Object Model                  │
│   (Page actions — Selenium WebDriver + POM)     │
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│                 Base Classes                     │
│  BaseClass (ThreadLocal Driver) | BasePage      │
└──────────────────────┬──────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────┐
│                  Utilities                       │
│  Config | Excel | Reports | Screenshot | Retry  │
└─────────────────────────────────────────────────┘
```

### Key Design Decisions

- **ThreadLocal WebDriver** — safe for parallel test execution
- **Page Object Model** — clean separation of page actions and test logic
- **PageFactory** — element initialisation via `@FindBy` annotations
- **`By` locator constants** — no XPath duplication across methods
- **Log4j2** — consistent logging to console and rolling file appender
- **ExtentReports** — rich HTML reports with screenshots on pass and fail
- **Retry Analyzer** — auto-retry flaky tests without manual intervention
- **Excel Data** — test data driven via Apache POI

---

## 🙋‍♀️ Author

**Diksha Bandagale**
Senior QA Automation Engineer | SDET
📧 dikshabandagale0497@gmail.com
🔗 [LinkedIn](https://www.linkedin.com/in/diksha-bandagale)
🐙 [GitHub](https://github.com/dikshab0497)

---

## 📄 License

This project is built for portfolio and demonstration purposes.
