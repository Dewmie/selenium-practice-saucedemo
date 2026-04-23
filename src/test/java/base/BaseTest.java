package base;
//writing the setup once, and every test file simply inherits it. That's it.

//This is a core programming principle called DRY — Don't Repeat Yourself

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod  //opens Chrome before each test
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");

    }

    @AfterMethod  //closes Chrome after each test
    public void tearDown(){ //only quit if driver was actually created
        if(driver != null){
            driver.quit();  // Closes browser AND stops ChromeDriver process
        }
    }

}
