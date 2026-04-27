package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;

    //----Change the browser name here - chrome/ edge/ firefox
    private static final String browser = "edge";

    @BeforeMethod
    public void setUp() {
        //driver = new EdgeDriver();
        //driver = new ChromeDriver();

        driver = createDriver(browser);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");

    }

    private WebDriver createDriver(String browser){

        switch (browser.toLowerCase()){

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

    @AfterMethod  //closes browser after each test
    public void tearDown(){ //only quit if driver was actually created
        if(driver != null){
            driver.quit();
        }
    }

}
