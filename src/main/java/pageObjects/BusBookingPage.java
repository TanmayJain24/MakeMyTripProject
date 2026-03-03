package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.CommonCode;
import utilities.Log;
import java.time.Duration;
import java.util.List;

public class BusBookingPage {
    private final CommonCode common;
    private final WebDriver driver;

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

    @FindBy(xpath = "//span[contains(text(),'FASTEST')]")
    private WebElement durationSortBtn;

    @FindBy(xpath = "(//p[starts-with(@class,'SrpActiveCardstyles__BusBoldtxtPara')])[1]")
    private WebElement firstBusName;

    @FindBy(xpath = "(//span[contains(@class,'SrpActiveCardstyles__DurationDiv-sc-yk')])[1]")
    private WebElement firstBusDuration;

    @FindBy(xpath = "//p[text()='Top Rated']")
    private WebElement topRatedFilter;

    @FindBy(xpath = "//span[contains(text(),'RATING')]")
    private WebElement ratingSortHeader;

    @FindBy(xpath = "(//p[contains(@class,'SrpActiveCardstyles__BusBoldtxtPara-sc-yk')])[1]")
    private WebElement firstBusNameP5;

    @FindBy(xpath = "(//span[contains(@class,'SrpActiveCardstyles__BusReviewHighRatingSpan-sc-yk')])[1]")
    private WebElement firstBusRating;

    public BusBookingPage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.common = new CommonCode(driver, timeout);
        PageFactory.initElements(driver, this);
        Log.info("Bus Booking Page initialized.");
    }
    public void navigateToBuses() {
        Log.info("Navigating to Bus Tab.");
        common.safeClickToWebElement(busTab);
    }
    public void enterFromCity(String from) {
        Log.info("Entering source city: " + from);
        common.scrollClearType(sourceInput, from);
        By suggestion = By.xpath("//li//span[contains(translate(text(), 'PUNE', 'pune'), 'pune')]");
        try {
            common.safeClick(suggestion);
        } catch (Exception e) {
            sourceInput.sendKeys(Keys.ENTER);
            Log.info("Suggestion not clickable, pressed Enter.");
        }
    }
    public void enterToCity(String to) {
        Log.info("Entering destination city: " + to);
        common.scrollClearType(destinationInput, to);
        By suggestion = By.xpath("//li//span[contains(translate(text(), 'INDORE', 'indore'), 'indore')]");
        try {
            common.safeClick(suggestion);
        } catch (Exception e) {
            destinationInput.sendKeys(Keys.ENTER);
            Log.info("Suggestion not clickable, pressed Enter.");
        }
    }
    public void selectTomorrow() {
        Log.info("Selecting Tomorrow's date.");
        common.safeClickToWebElement(tomorrowBtn);
    }
    public void clickSearch() {
        Log.info("Clicking Search button.");
        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        common.safeClickToWebElement(searchBtn);
    }
    public int getBusCount() {
        try {
            List<WebElement> results = common.allVisible(busCards);
            int count = results.size();
            Log.info("Buses found: " + count);
            return count;
        } catch (TimeoutException e) {
            Log.info("No buses found within timeout.");
            return 0;
        }
    }
    public void applyFilters() {
        Log.info("Applying AC and Sleeper filters.");
        common.safeClickToWebElement(acFilter);
        common.safeClickToWebElement(sleeperFilter);
    }
    public void sortByFastest() {
        Log.info("Sorting by Duration (Fastest).");
        common.safeClickToWebElement(durationSortBtn);
    }
    public String getFirstBusDetails() {
        try {
            String name = common.visible(firstBusName).getText().trim();
            String duration = common.visible(firstBusDuration).getText().trim();
            return "Bus Name: " + name + " | Travel Time: " + duration;
        } catch (Exception e) {
            Log.error("Failed to extract bus details: " + e.getMessage());
            return "Could not extract bus details.";
        }
    }
    public void applyTopRatedFilter() {
        Log.info("Applying Top Rated filter.");
        common.safeClickToWebElement(topRatedFilter);
    }
    public void sortByRatingDescending() {
        Log.info("Sorting by Rating.");
        common.safeClickToWebElement(ratingSortHeader);
    }
    public String getTopRatedBusDetails() {
        try {
            String name = common.visible(firstBusNameP5).getText().replaceAll("\\s+", " ").trim();
            String rating = common.visible(firstBusRating).getText().replaceAll("\\s+", " ").trim();
            return "Bus Name: " + name + " | Rating: " + rating;
        } catch (Exception e) {
            Log.error("Error retrieving top-rated bus: " + e.getMessage());
            return "Could not retrieve bus details.";
        }
    }
}