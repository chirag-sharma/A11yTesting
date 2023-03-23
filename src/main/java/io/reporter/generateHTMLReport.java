package io.reporter;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class generateHTMLReport {

    public static void generateReports() {
    //public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.get(System.getProperty("user.dir")+"\\htmlReporter\\index.html");
        webDriver.manage().window().maximize();

        //upload file
        webDriver.findElement(By.xpath("//input[@id='file-upload']")).sendKeys(System.getProperty("user.dir")+"\\reports\\accessibilityReport.json");
        webDriver.findElement(By.xpath("//*[text()='Generate Report']")).click();


    }

    public static void generateReport(String testType) {
        //public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.get(System.getProperty("user.dir")+"\\htmlReporter\\index.html");
        webDriver.manage().window().maximize();

        //upload file
        if(testType.equalsIgnoreCase("accessibilityReport_FullPage")){
            webDriver.findElement(By.xpath("//input[@id='file-upload']")).sendKeys(System.getProperty("user.dir")+"\\reports\\accessibilityReport_FullPage.json");
        } else if (testType.equalsIgnoreCase("includedSelectorsReport")) {
            webDriver.findElement(By.xpath("//input[@id='file-upload']")).sendKeys(System.getProperty("user.dir")+"\\reports\\includedSelectorsReport.json");
        }else if (testType.equalsIgnoreCase("excludedSelectorsReport")) {
            webDriver.findElement(By.xpath("//input[@id='file-upload']")).sendKeys(System.getProperty("user.dir")+"\\reports\\excludedSelectorsReport.json");
        }

        webDriver.findElement(By.xpath("//*[text()='Generate Report']")).click();


    }

}
