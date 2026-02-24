package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class CabBookingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By cabsBtn = By.xpath("//span[text()='Cabs']");
    private By oneWayLocator = By.xpath("//span[normalize-space()='Outstation One-way']");
    private By fromCityInput = By.xpath("//input[@id='downshift-1-input']");
    private By toCityInput = By.xpath("//input[@id='downshift-2-input']");
    private By departureLabel = By.xpath("//div[@class='HomeSearchWidgetstyles__PickupDate-sc-1tz7y2x-6 fvVrBA']//span[@class='HomeSearchWidgetstyles__DateTxt-sc-1tz7y2x-7 dmEwXM']");
    private By calendarHeader = By.xpath("//p[@class='dcalendarstyles__MonthNamePara-sc-s6w5s3-3 kvJFRU']"); // adjust to your DOM
    private By nextMonthButton = By.xpath("//div[@class='dcalendarstyles__MonthChangeRightArrowDiv-sc-s6w5s3-16 cRfdOs']"); // adjust to your DOM
    private By pickupLocator = By.xpath("//div[@class='HomeSearchWidgetstyles__PickupTime-sc-1tz7y2x-8 fqIfzQ']/span");
    private By pickupDropdown = By.xpath("//section[@class='TimeDropdownstyles__TimeDropdown-sc-d6504d-0 fREELa']/ul/li");
    private By searchButton = By.xpath("//button[normalize-space()='SEARCH CABS']");
    private By cabPrices = By.xpath("//span[@class='cabDetailsCard_price__SHN6W']");
    private By cabCards = By.xpath("//div[@class='cabDetailsCard_cabDetails__X3Adv']");
    private By selectBtnLocator = By.xpath("//span[normalize-space(text())='SELECT CAB']");
    private By cabDate = By.xpath("//span[@class='sc-fWnslK sc-kpOvIu dsTpEE journeyduration_journeyDurationText__Q4fcc']");

    //Constructor
    public CabBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Navigate to Cabs page
    public void openCabsPage() {
        wait.until(ExpectedConditions.elementToBeClickable(cabsBtn)).click();
        System.out.println("Navigated to Cabs page.");
    }

    public boolean clickOneWayOutstation(){
        WebElement oneWayOutstationBtn = wait.until(ExpectedConditions.elementToBeClickable(oneWayLocator));
        try{
            oneWayOutstationBtn.click();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // Select From city with dropdown
    public boolean selectFromCity(String from) {
        wait.until(ExpectedConditions.elementToBeClickable(fromCityInput)).click();
        wait.until(ExpectedConditions.elementToBeClickable(fromCityInput)).sendKeys(from);
        By fromOptionLocator = By.xpath("//p[contains(text(),'" + from + "')]");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", fromOption);
        System.out.println("Selected From city: " + from);
        wait.until(ExpectedConditions.textToBePresentInElementValue(fromCityInput, from));
        String fromCityText = wait.until(ExpectedConditions.elementToBeClickable(fromCityInput)).getAttribute("value");
        return fromCityText.contains(from);
    }

    // Select To city with dropdown
    public boolean selectToCity(String to) {
        wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).click();
        wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).sendKeys(to);
        By fromOptionLocator = By.xpath("//p[contains(text(),'" + to + "')]");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", fromOption);
        System.out.println("Selected To city: " + to);
        wait.until(ExpectedConditions.textToBePresentInElementValue(toCityInput, to));
        String toCityText = wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).getAttribute("value");
        return toCityText.contains(to);
    }

    public boolean selectDepartureDate(String day, String targetMonthYear) {
        wait.until(ExpectedConditions.elementToBeClickable(departureLabel)).click();
        String currentMonthYear = wait.until(ExpectedConditions.visibilityOfElementLocated(calendarHeader)).getText();
        while (!currentMonthYear.equalsIgnoreCase(targetMonthYear)) {
            WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(nextMonthButton));
            nextBtn.click();
            currentMonthYear = wait.until(ExpectedConditions.visibilityOfElementLocated(calendarHeader)).getText();
        }
        By dateLocator = By.xpath("//ul[contains(@class,'DateWrapDiv')]//span[normalize-space(text())='" + day + "']");
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(dateLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateElement);
        System.out.println("Selected departure date: " + day + " " + targetMonthYear);
        String selectedDate = wait.until(ExpectedConditions.visibilityOfElementLocated(departureLabel)).getText();
        return selectedDate.contains(day);
    }

    //Select pick-up Time
    public boolean selectPickupTime(String time) {
        // Locate all the <li> elements inside the timepicker dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(pickupLocator));
        dropdown.click();
        List<WebElement> timeOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(pickupDropdown));
        for (WebElement option : timeOptions) {
            String timeText = option.getText().trim();
            if (timeText.equalsIgnoreCase(time)) {
                option.click();
                break;
            }
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pickupLocator)).getText().trim().equals(time);
    }

    // Click Search
    public void searchCabs() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        System.out.println("Search button clicked.");
    }

    // Select Cab Type
    public boolean selectCabType(String cabType) {
        WebElement cabTypeSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='" + cabType + "']")));
        cabTypeSelect.click();
        System.out.println("Cab Type selected");
        WebElement cabTypeCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='checkbox'][.//span[text()='" + cabType + "']]")));
        return "true".equalsIgnoreCase(cabTypeCheckbox.getAttribute("aria-checked"));
    }

    // Print the price of Lowest Cab
    public int printLowestCabPrice() {
        List<WebElement> priceElements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(cabPrices)
        );

        List<Integer> prices = new ArrayList<>();
        System.out.print("Available prices: ");
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
        System.out.println();
        int lowestPrice = 0;
        if (!prices.isEmpty()) {
            lowestPrice = Collections.min(prices); // store lowest price
            System.out.println("Lowest cab price: " + lowestPrice);
        } else {
            System.out.println("No cab prices found.");
        }
        return lowestPrice;
    }

    // Select Lowest Price Cab
    public void selectLowestCab() {
        // Wait until all cab cards are visible
        List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cabCards));
        int lowestPrice = printLowestCabPrice();
        for (WebElement card : cards) {
            // Get the price text from each card
            WebElement priceElement = card.findElement(cabPrices);
            String priceText = priceElement.getText().replaceAll("[^0-9]", "");
            if (!priceText.isEmpty()) {
                int price = Integer.parseInt(priceText);
                // Compare with the lowestPrice you already calculated earlier
                if (price == lowestPrice) {
                    WebElement selectBtn = card.findElement(selectBtnLocator);
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", selectBtn);
                    wait.until(ExpectedConditions.elementToBeClickable(selectBtn)).click();
                    System.out.println("Clicked Select Cab for price: " + lowestPrice);
                    return;
                }
            }
        }
        System.out.println("Lowest cab card not found.");
    }

    // Validate the date displayed on UI is same as expected
    public boolean validateDate(String expectedDate) {
        WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cabDate));
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
        System.out.println("Validation " + (result ? "PASSED" : "FAILED") + " → Expected: " + expectedParsed + " | Actual: " + uiParsed.toLocalDate());
        return result;
    }
}