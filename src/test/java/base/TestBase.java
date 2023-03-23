package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.reporter.generateHTMLReport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static base.AccessibilityBase.testType;

public class TestBase {
        private static WebDriver driver;

        @BeforeTest
        public void setup()
        {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        }

        @AfterTest
        public void tearDown()
        {
            if(driver != null)
                driver.quit();
            generateHTMLReport.generateReport(testType);
        }

       public static WebDriver webDriver()
        {
            return driver;
        }
}

