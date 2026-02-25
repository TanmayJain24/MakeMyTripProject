package basetest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utilities.ConfigReader;
import java.time.Duration;
import utilities.Log;

public class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    //Locators
    By closeBtn = By.xpath("//span[@role='presentation']");

    @BeforeSuite
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("baseUrl");

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("disable-infobars");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " + "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/120.0.0.0 Safari/537.36");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--disable-notifications");
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        driver.manage().window().maximize();
        int implicitWait = Integer.parseInt(ConfigReader.getProperty("implicitWait"));
        int explicitWait = Integer.parseInt(ConfigReader.getProperty("explicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        driver.get(url);
        handleLoginPopup();
    }

    public void handleLoginPopup() {
        try {
            // Wait up to 5 seconds for popup close button
            WebElement popupClose = wait.until(ExpectedConditions.visibilityOfElementLocated(closeBtn));
            popupClose.click();
            Log.info("Login popup closed.");
        } catch (Exception e) {
            // Popup not present within timeout
            Log.info("No login popup displayed.");
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}