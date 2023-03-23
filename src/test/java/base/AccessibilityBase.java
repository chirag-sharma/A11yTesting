package base;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccessibilityBase extends TestBase
{
    private static List<String> tags = Arrays.asList("wcag2a", "wcag2aa");
    private static String reportPath = System.getProperty("user.dir") + "\\reports\\";

    public static String testType = "";

    public static void checkAccessibilityViolations() throws IOException
    {
        testType="accessibilityReport_FullPage";
        String reportFile = reportPath + testType;
        WebDriver webDriver = webDriver();
        webDriver.manage().window().maximize();
        //webDriver.findElement(By.xpath("//*[text()='Accept All']")).click();
        AxeBuilder builder = new AxeBuilder();
        builder.withTags(tags);
        Results results = builder.analyze(webDriver);
        saveReport(results, reportFile);
    }

    public static void checkAccessibilityIncludingSelector(String selector) throws FileNotFoundException
    {
        testType = "includedSelectorsReport";
        String reportFile = reportPath + testType;
        WebDriver webDriver = webDriver();
        webDriver.manage().window().maximize();
        AxeBuilder builder = new AxeBuilder();
        builder.withTags(tags);
        //Results results = builder.include(Collections.singletonList(selector)).analyze(webDriver);
        Results results = builder.include(Collections.singletonList(selector)).analyze(webDriver);
        System.out.println(results.getViolations());
        saveReport(results, reportFile);
    }

    public static void checkAccessibilityExcludingSelector(String selector) throws FileNotFoundException
    {
        testType = "excludedSelectorsReport";
        String reportFile = reportPath + testType;
        WebDriver webDriver = webDriver();
        webDriver.manage().window().maximize();
        AxeBuilder builder = new AxeBuilder();
        builder.withTags(tags);
        //Results results = builder.include(Collections.singletonList(selector)).analyze(webDriver);
        Results results = builder.exclude(Collections.singletonList(selector)).analyze(webDriver);
        System.out.println(results.getViolations());
        saveReport(results, reportFile);
    }

    public static void saveReport(Results results, String reportFile) throws FileNotFoundException {

        try{
            List<Rule> violations = results.getViolations();
            if (violations.size() == 0)
            {
                Assert.assertTrue(true, "No violations found");
            }
            else
            {
                JsonParser jsonParser = new JsonParser();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                AxeReporter.writeResultsToJsonFile(reportFile, results);
                JsonElement jsonElement = jsonParser.parse(new FileReader(reportFile + ".json"));
                String prettyJson = gson.toJson(jsonElement);
                AxeReporter.writeResultsToTextFile(reportFile, prettyJson);
                Assert.assertEquals(violations.size(), 0, violations.size() + " violations found");
            }
        } catch(RuntimeException exception){
            throw new RuntimeException(exception.getMessage());
        } catch(FileNotFoundException exception){
            throw new FileNotFoundException(exception.getMessage());
        }

    }
}

