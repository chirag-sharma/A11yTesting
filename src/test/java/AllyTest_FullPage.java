import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.reporter.generateHTMLReport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.List;

public class AllyTest_FullPage {


    private static String reportPath = System.getProperty("user.dir") + "\\reports\\";
    String reportFile = reportPath + "accessibilityReport";
    public WebDriver webDriver=null;
    public AxeBuilder axeBuilder=null;
    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        axeBuilder = new AxeBuilder();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
    }
    @Test
    public void testMyWebPage() {

        webDriver.get("https://www.icicibank.com/");
        //webDriver.get("https://www.google.com/");

        try {
            Results axeResults = axeBuilder.analyze(webDriver);
            System.out.println(axeResults.getViolations());
            List<Rule> violations = axeResults.getViolations();
            if (violations.size() == 0)
            {
                Assert.assertTrue(true, "No violations found");
            }
            else
            {
                JsonParser jsonParser = new JsonParser();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                AxeReporter.writeResultsToJsonFile(reportFile, axeResults);
                JsonElement jsonElement = jsonParser.parse(new FileReader(reportFile + ".json"));
                String prettyJson = gson.toJson(jsonElement);
                AxeReporter.writeResultsToTextFile(reportFile, prettyJson);
                Assert.assertEquals(violations.size(), 0, violations.size() + " violations found");

            }
            //assertTrue(axeResults.violationFree());
        } catch (RuntimeException e) {
            // Do something with the error
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @AfterMethod
    public void tearDown(){
        webDriver.close();
        generateHTMLReport.generateReports();
    }
}