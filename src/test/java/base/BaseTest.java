package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;

    // Two things needed for reporting
    private static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>(); //ThreadLocal — each test has its own copy

    //  Runs once before all tests
    @BeforeSuite
    public void startReport() {
        // Tell it where to save the report
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/TestReport.html");

        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setReportName("My Selenium Test Results");
        reporter.config().setDocumentTitle("SauceDemo Test Report");

        //  Attach reporter and adding information
        extent = new ExtentReports();
        extent.setSystemInfo("Tester", "Dewmie Wijewardhana");
        extent.setSystemInfo("Project", "SauceDemo E-commerce Website Testing");
        extent.setSystemInfo("Browser", ConfigReader.getBrowser().toUpperCase());
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("OS", "Windows");
        extent.attachReporter(reporter);

    }

    @BeforeMethod
    public void setUp(java.lang.reflect.Method method) {

        // Fallback if @BeforeSuite didn't run
        if (extent == null) {
            startReport();
        }

        // test name — class + method
        String testName = method.getDeclaringClass().getSimpleName()
                + " - " + method.getName();
        test.set(extent.createTest(testName));

        // 2. Initialize browser
        String browser = ConfigReader.getBrowser();
        driver = createDriver(browser);
        driver.manage().window().maximize();
        driver.get(ConfigReader.getBaseUrl());
    }

    private WebDriver createDriver(String browser) {

        switch (browser.toLowerCase()) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                // for disable password manager in chrome
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_leak_detection", false);
                chromeOptions.setExperimentalOption("prefs", prefs);

                return new ChromeDriver(chromeOptions);

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);

            default:
                throw new IllegalArgumentException(
                        "Browser not supported: " + browser + ". Use: chrome, edge, or firefox");
        }
    }

    //---Runs after EACH test
    @AfterMethod
    public void tearDown(ITestResult result) {

        // Mark test as pass or fail in report
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().pass("PASSED");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.get().fail(result.getThrowable());
        } else {
            test.get().skip("SKIPPED");
        }
        extent.flush();

        // Close browser
        if (driver != null) {
            driver.quit();
        }
    }


    // ---Runs ONCE after all tests ------
    @AfterSuite
    public void endReport() {
        // Write the HTML file to disk
        extent.flush();
    }

}
