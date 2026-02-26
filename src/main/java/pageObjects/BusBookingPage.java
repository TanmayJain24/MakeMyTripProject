package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class BusBookingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[text()='Bus']")
    private WebElement busTab;
    @FindBy(xpath = "//input[@name='autosuggestBusSRPSrcHomeName']")
    private WebElement sourceInput;
    @FindBy(xpath = "//input[@name='autosuggest']")
    private WebElement destinationInput;
    @FindBy(xpath = "//span[text()='Tomorrow'] | //button[text()='Tomorrow']")
    private WebElement tomorrowBtn;
    @FindBy(xpath = "//button[text()='Search Bus']")
    private WebElement searchBtn;
    @FindBy(xpath = "//div[contains(@class,'ActivepcardInnerLayoutDiv')]")
    private List<WebElement> busCards;
    @FindBy(xpath = "//div[text()='AC']")
    private WebElement acFilter;
    @FindBy(xpath = "//div[text()='Sleeper']")
    private WebElement sleeperFilter;
    @FindBy(xpath = "//div[contains(@class, 'loading')] | //img[contains(@src, 'loader')]")
    private WebElement loader;
    @FindBy(xpath = "//span[contains(text(),'FASTEST')]")
    private WebElement durationSortBtn;
    @FindBy(xpath = "(//p[@class='SrpActiveCardstyles__BusBoldtxtPara-sc-yk1110-7 fqXqTO'])[1]")
    private WebElement firstBusName;
    @FindBy(xpath = "(//span[@class='SrpActiveCardstyles__DurationDiv-sc-yk1110-23 hfNSuO'])[1]")
    private WebElement firstBusDuration;
    @FindBy(xpath = "//p[text()='Top Rated']")
    private WebElement topRatedFilter;
    @FindBy(xpath = "//span[contains(text(),'RATING')]")
    private WebElement ratingSortHeader;
    @FindBy(xpath = "(//p[@class='SrpActiveCardstyles__BusBoldtxtPara-sc-yk1110-7 fqXqTO'])[1]")
    private WebElement firstBusNameP5;
    @FindBy(xpath = "(//span[@class='SrpActiveCardstyles__BusReviewHighRatingSpan-sc-yk1110-13 dCCUco'])[1]")
    private WebElement firstBusRating;

    public BusBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }
    public void navigateToBuses() {
        wait.until(ExpectedConditions.elementToBeClickable(busTab)).click();
    }
    public void enterFromCity(String from) {
        WebElement src = wait.until(ExpectedConditions.visibilityOf(sourceInput));
        src.clear();
        src.sendKeys(from);
        By suggestion = By.xpath("//li//span[contains(translate(text(), 'PUNE', 'pune'), 'pune')]");
        try {
            WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(suggestion));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
        } catch (Exception e) {
            src.sendKeys(Keys.ENTER);
        }
    }
    public void enterToCity(String to) {
        WebElement dest = wait.until(ExpectedConditions.visibilityOf(destinationInput));
        dest.clear();
        dest.sendKeys(to);
        By suggestion = By.xpath("//li//span[contains(translate(text(), 'INDORE', 'indore'), 'indore')]");
        try {
            WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(suggestion));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
        } catch (Exception e) {
            dest.sendKeys(Keys.ENTER);
        }
    }
    public void selectTomorrow() {
        try {
            WebElement tomorrow = wait.until(ExpectedConditions.elementToBeClickable(tomorrowBtn));
            tomorrow.click();
            System.out.println("Selected Tomorrow's date.");
        } catch (Exception e) {
            System.out.println("Tomorrow button not found or already selected.");
        }
    }
    public void clickSearch() {
        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
            WebElement btn = wait.until(ExpectedConditions.visibilityOf(searchBtn));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", btn);
            System.out.println("Search button clicked.");
        } catch (Exception e) {
            System.out.println("Search button click failed: " + e.getMessage());
        }
    }
    public int getBusCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(busCards));
            List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElements(busCards));
            return results.size();
        } catch (TimeoutException e) {
            return 0;
        }
    }
    public void applyFilters() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement ac = wait.until(ExpectedConditions.elementToBeClickable(acFilter));
        js.executeScript("arguments[0].click();", ac);
        System.out.println("AC Filter applied.");
        WebElement sleeper = wait.until(ExpectedConditions.elementToBeClickable(sleeperFilter));
        js.executeScript("arguments[0].click();", sleeper);
        System.out.println("Sleeper Filter applied.");
    }
    public void sortByFastest() {
        try {
            WebElement sortBtn = wait.until(ExpectedConditions.elementToBeClickable(durationSortBtn));
            sortBtn.click();
            System.out.println("Clicked on Duration Sort.");
        } catch (Exception e) {
            System.out.println("Could not click Sort by Duration: " + e.getMessage());
        }
    }
    public String getFirstBusDetails() {
        try {
            String name = wait.until(ExpectedConditions.visibilityOf(firstBusName)).getText();
            String duration = wait.until(ExpectedConditions.visibilityOf(firstBusDuration)).getText();            return "Bus Name: " + name + " | Travel Time: " + duration;
        } catch (Exception e) {
            return "Could not extract bus details.";
        }
    }
    public void applyTopRatedFilter() {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(topRatedFilter));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    public void sortByRatingDescending() {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(ratingSortHeader));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
    public String getTopRatedBusDetails() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstBusNameP5));
            String name = wait.until(ExpectedConditions.visibilityOf(firstBusNameP5)).getText();
            String rating = wait.until(ExpectedConditions.visibilityOf(firstBusRating)).getText();
            String cleanName = name.replaceAll("\\s+", " ").trim();
            String cleanRating = rating.replaceAll("\\s+", " ").trim();
            return "Bus Name: " + cleanName + " | Rating: " + cleanRating;
        } catch (Exception e) {
            return "Could not retrieve bus details: " + e.getMessage();
        }
    }
}
