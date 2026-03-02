package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.CommonCode;
import utilities.Log;

public class CabBookingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private CommonCode common;

    public CabBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.common = new CommonCode(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='Cabs']")
    private WebElement cabsBtn;

    @FindBy(xpath = "//span[normalize-space()='Outstation One-way']")
    private WebElement oneWayLocator;

    @FindBy(xpath = "//input[@id='downshift-1-input']")
    private WebElement fromCityInput;

    @FindBy(xpath = "//input[@id='downshift-2-input']")
    private WebElement toCityInput;

    @FindBy(xpath = "//div[@class='HomeSearchWidgetstyles__PickupDate-sc-1tz7y2x-6 fvVrBA']//span[@class='HomeSearchWidgetstyles__DateTxt-sc-1tz7y2x-7 dmEwXM']")
    private WebElement departureLabel;

    @FindBy(xpath = "//p[@class='dcalendarstyles__MonthNamePara-sc-s6w5s3-3 kvJFRU']")
    private WebElement calendarHeader;

    @FindBy(xpath = "//div[@class='dcalendarstyles__MonthChangeRightArrowDiv-sc-s6w5s3-16 cRfdOs']")
    private WebElement nextMonthBtn;

    @FindBy(xpath = "//div[@class='HomeSearchWidgetstyles__PickupTime-sc-1tz7y2x-8 fqIfzQ']/span")
    private WebElement pickUpTime;

    @FindBy(xpath = "//section[@class='TimeDropdownstyles__TimeDropdown-sc-d6504d-0 fREELa']/ul/li")
    private List<WebElement> pickupDropdown;

    @FindBy(xpath = "//button[normalize-space()='SEARCH CABS']")
    private WebElement searchBtn;

    @FindBy(xpath = "//span[@class='cabDetailsCard_price__SHN6W']")
    private List<WebElement> cabPrices;

    @FindBy(xpath = "//div[@class='cabDetailsCard_cabDetails__X3Adv']")
    private List<WebElement> cabCards;

    @FindBy(xpath = "//span[normalize-space(text())='SELECT CAB']")
    private List<WebElement> selectBtns;

    @FindBy(xpath = "//span[@class='sc-fWnslK sc-kpOvIu dsTpEE journeyduration_journeyDurationText__Q4fcc']")
    private WebElement cabDate;

    // Navigate to Cabs page
    public void openCabsPage() {
        common.clickWhenClickable(cabsBtn);
        Log.info("Navigated to Cabs page.");
    }

    // Click One Way Outstation Cab option
    public boolean clickOneWayOutstation() {
        try {
            common.clickWhenClickable(oneWayLocator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Select From city with dropdown
    public boolean selectFromCity(String from) {
        common.clickWhenClickable(fromCityInput);
        common.enterText(fromCityInput, from);
        By fromOptionLocator = By.xpath("//p[contains(text(),'" + from + "')]");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", fromOption);
        Log.info("Selected From city: " + from);
        wait.until(ExpectedConditions.textToBePresentInElementValue(fromCityInput, from));
        String fromCityText = common.waitUntilClickable(fromCityInput).getAttribute("value");
        return fromCityText.contains(from);
    }

    // Select To city with dropdown
    public boolean selectToCity(String to) {
        common.clickWhenClickable(toCityInput);
        common.enterText(toCityInput, to);
        By fromOptionLocator = By.xpath("//p[contains(text(),'" + to + "')]");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", fromOption);
        Log.info("Selected To city: " + to);
        wait.until(ExpectedConditions.textToBePresentInElementValue(toCityInput, to));
        String toCityText = common.waitUntilClickable(toCityInput).getAttribute("value");
        return toCityText.contains(to);
    }

    // Select the Departure date
    public boolean selectDepartureDate(String day, String targetMonthYear) {
        common.clickWhenClickable(departureLabel);
        String currentMonthYear = wait.until(ExpectedConditions.visibilityOf(calendarHeader)).getText();
        while (!currentMonthYear.equalsIgnoreCase(targetMonthYear)) {
            common.clickWhenClickable(nextMonthBtn);
            currentMonthYear = wait.until(ExpectedConditions.visibilityOf(calendarHeader)).getText();
        }
        By dateLocator = By.xpath("//ul[contains(@class,'DateWrapDiv')]//span[normalize-space(text())='" + day + "']");
        common.safeClick(dateLocator);
        Log.info("Selected departure date: " + day + " " + targetMonthYear);
        String selectedDate = wait.until(ExpectedConditions.visibilityOf(departureLabel)).getText();
        return selectedDate.contains(day);
    }

    //Select pick-up Time
    public boolean selectPickupTime(String time) {
        // Locate all the <li> elements inside the timepicker dropdown
        common.clickWhenClickable(pickUpTime);
        List<WebElement> timeOptions = wait.until(ExpectedConditions.visibilityOfAllElements(pickupDropdown));
        for (WebElement option : timeOptions) {
            String timeText = option.getText().trim();
            if (timeText.equalsIgnoreCase(time)) {
                option.click();
                Log.info("Selected pickup time: " + time);
                break;
            }
        }
        return wait.until(ExpectedConditions.visibilityOf(pickUpTime)).getText().trim().equals(time);
    }

    // Click Search
    public void searchCabs() {
        common.clickWhenClickable(searchBtn);
        Log.info("Search button clicked.");
    }

    // Select Cab Type
    public boolean selectCabType(String cabType) {
        WebElement cabTypeSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + cabType + "']")));
        cabTypeSelect.click();
        Log.info("Cab Type selected");
        WebElement cabTypeCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='checkbox'][.//span[text()='" + cabType + "']]")));
        return "true".equalsIgnoreCase(cabTypeCheckbox.getAttribute("aria-checked"));
    }

    // Print the price of Lowest Cab
    public int printLowestCabPrice() {
        List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElements(cabPrices));

        List<Integer> prices = new ArrayList<>();
        Log.info("Available prices: ");
        for (int i = 0; i < priceElements.size(); i++) {
            String priceText = priceElements.get(i).getText().replaceAll("[^0-9]", "");
            if (!priceText.isEmpty()) {
                prices.add(Integer.parseInt(priceText));
                System.out.print(priceText);
                if (i < priceElements.size() - 1) {
                    System.out.print(", ");
                }
            }
        }
        int lowestPrice = 0;
        if (!prices.isEmpty()) {
            lowestPrice = Collections.min(prices); // store lowest price
            Log.info("Lowest cab price: " + lowestPrice);
        } else {
            Log.info("No cab prices found.");
        }
        return lowestPrice;
    }

    // Select Lowest Price Cab
    public void selectLowestCab() {
        // Wait until all cab cards are visible
        List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElements(cabCards));
        int lowestPrice = printLowestCabPrice();
        for (int i = 0; i < cabCards.size(); i++) {
            String priceText = cabPrices.get(i).getText().replaceAll("[^0-9]", "");
            if (!priceText.isEmpty()) {
                int price = Integer.parseInt(priceText);
                if (price == lowestPrice) {
                    WebElement selectBtn = selectBtns.get(i);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectBtn);
                    wait.until(ExpectedConditions.elementToBeClickable(selectBtn)).click();
                    Log.info("Clicked Select Cab for price: " + lowestPrice);
                    return;
                }
            }
        }
        Log.info("Lowest cab card not found.");
    }

    // Validate the date displayed on UI is same as expected
    public boolean validateDate(String expectedDate) {
        WebElement dateElement = wait.until(ExpectedConditions.visibilityOf(cabDate));
        String uiDate = dateElement.getText().trim();

        // Formatter for UI string
        DateTimeFormatter uiFormatter = new DateTimeFormatterBuilder()
                .appendPattern("d MMM, h:mm a")
                .parseDefaulting(ChronoField.YEAR, 2026)
                .toFormatter(Locale.ENGLISH);

        // Formatter for expected string
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("EEE MMM d yyyy", Locale.ENGLISH);

        // Parse both
        LocalDateTime uiParsed = LocalDateTime.parse(uiDate, uiFormatter);
        LocalDate expectedParsed = LocalDate.parse(expectedDate, expectedFormatter);
        boolean result = uiParsed.toLocalDate().equals(expectedParsed);
        Log.info("Validation " + (result ? "PASSED" : "FAILED") + " → Expected: " + expectedParsed + " | Actual: " + uiParsed.toLocalDate());
        return result;
    }
}