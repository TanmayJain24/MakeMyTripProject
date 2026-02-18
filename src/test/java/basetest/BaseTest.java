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

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("baseUrl");

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--disable-notifications");
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        driver.manage().window().maximize();

        // Apply waits from config.properties
        int implicitWait = Integer.parseInt(ConfigReader.getProperty("implicitWait"));
        int explicitWait = Integer.parseInt(ConfigReader.getProperty("explicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        driver.get(url);
        handleLoginPopup();

    }

    public void handleLoginPopup() {
        By closeBtn = By.xpath("//span[@class='commonModal__close']");
        By BotBtn = By.xpath("//div[class='tp-dt-header-icon']");
        try {
            // Wait up to 5 seconds for popup close button
            WebElement popupClose = wait.until(ExpectedConditions.visibilityOfElementLocated(closeBtn));
            popupClose.click();
            System.out.println("Login popup closed.");
        } catch (Exception e) {
            // Popup not present within timeout
            System.out.println("No login popup displayed.");
        }

        try {
            // Close AI bot popup if present
            By aiBotCloseBtn = By.xpath("//img[@alt='minimize']");
            // adjust locator
            WebElement aiBotPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(aiBotCloseBtn));
            aiBotPopup.click();
            System.out.println("AI bot popup closed.");
        } catch (Exception e) {
            System.out.println("No AI bot popup displayed.");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}