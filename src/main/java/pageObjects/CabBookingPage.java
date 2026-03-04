package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
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
    WebDriver driver;
    WebDriverWait wait;
    CommonCode common;

    public CabBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.common = new CommonCode(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='Cabs']")
    WebElement cabsBtn;

    @FindBy(xpath = "//input[@id='downshift-1-input']")
    WebElement fromCityInput;

    @FindBy(xpath = "//input[@id='downshift-2-input']")
    WebElement toCityInput;

    @FindBy(xpath = "//label[contains(text(), 'Pickup Date')]/following-sibling::span")
    WebElement pickUpLabel;

    @FindBy(xpath = "//div[@data-testid='calendarLeftArrowBtn']/following-sibling::p")
    WebElement calendarHeader;

    @FindBy(xpath = "//div[@data-testid='calendarRightArrowBtn']")
    WebElement nextMonthBtn;

    @FindBy(xpath = "//label[contains(text(), 'Pickup Time')]/following-sibling::span")
    WebElement pickUpTime;

    @FindBy(xpath = "//button[contains(text(), 'SEARCH CABS')]")
    WebElement searchBtn;

    @FindBy(xpath = "//span[contains(@class, 'journeyduration_journeyDurationText')]")
    WebElement cabDate;

    @FindBy(xpath = "//input[@label='FULL NAME']")
    WebElement travelerNameField;

    @FindBy(xpath = "//div[@role='button']")
    WebElement travelerGenderField;

    @FindBy(xpath = "//input[@type='number']")
    WebElement travelerMobileField;

    @FindBy(xpath = "//input[@type='email']")
    WebElement travelerEmailField;

    @FindBy(xpath = "//span[text()='Full Pay']/following::span[@class='sc-fWnslK sc-kpOvIu bxZKYh']")
    WebElement priceTag;

    By cabCards = By.xpath("//div[contains(@class, 'cabDetailsCard_cabDetails_')]");
    By cabNames = By.xpath("//span[@data-testid='CAB_TITLE']");
    By cabPrices = By.xpath("//span[contains(@class, 'cabDetailsCard_price')]");
    By cabRating = By.xpath("//span[@class='rating_rating__diqPU']");
    By selectBtns = By.xpath("//span[contains(text(), 'SELECT CAB')]");
    By first = By.xpath("//span[normalize-space()='Hatchback']");
    By second = By.xpath("//span[normalize-space()='Sedan']")  ;
    By third = By.xpath("//span[normalize-space()='SUV']");

    public void openCabsPage() {
        common.clickWhenClickable(cabsBtn);
        Log.info("Navigated to Cabs page.");
    }

    public boolean clickCabServices(String cabService) {
        By serviceType = By.xpath("//span[normalize-space()='" + cabService + "']");
        try {
            common.safeClick(serviceType);
            Log.info("Trip Type Selected");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectPickupLocation(String from) {
        common.clickWhenClickable(fromCityInput);
        common.enterText(fromCityInput, from);
        By fromOptionLocator = By.xpath("//p[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + from + "')]");
        WebElement fromOption = common.visible(fromOptionLocator);
        common.safeClickToWebElement(fromOption);
        Log.info("Selected From city: " + from);
        common.waitForInputToHaveText(fromCityInput);
        String fromCityText = common.waitUntilClickable(fromCityInput).getAttribute("value").toLowerCase();
        return fromCityText.contains(from);
    }

    public boolean selectDropLocation(String to) {
        common.clickWhenClickable(toCityInput);
        common.enterText(toCityInput, to);
        By toOptionLocator = By.xpath("//p[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + to + "')]");
        WebElement toOption = common.visible(toOptionLocator);
        common.safeClickToWebElement(toOption);
        Log.info("Selected To city: " + to);
        common.waitForInputToHaveText(toCityInput);
        String toCityText = common.waitUntilClickable(toCityInput).getAttribute("value").toLowerCase();
        return toCityText.contains(to);
    }

    public boolean selectPickUpDate(String day, String targetMonthYear) {
        common.clickWhenClickable(pickUpLabel);
        String currentMonthYear = common.visible(calendarHeader).getText();
        while (!currentMonthYear.equalsIgnoreCase(targetMonthYear)) {
            common.clickWhenClickable(nextMonthBtn);
            currentMonthYear = common.visible(calendarHeader).getText();
        }
        By dateLocator = By.xpath("//ul[contains(@class,'DateWrapDiv')]//span[normalize-space(text())='" + day + "']");
        common.safeClick(dateLocator);
        Log.info("Selected departure date: " + day + " " + targetMonthYear);
        String selectedDate = common.visible(pickUpLabel).getText();
        return selectedDate.contains(day);
    }

    public boolean selectPickupTime(String time) {
        common.clickWhenClickable(pickUpTime);
        By optionLocator = By.xpath("//section[contains(@class,'TimeDropdown')]//li[span[text()='" + time + "']]");
        WebElement option = common.visible(optionLocator);
        common.clickWhenClickable(option);
        return common.visible(pickUpTime).getText().trim().equals(time);
    }

    public void selectRentalHours(String hoursText) {
        By hrsOption = By.xpath("//span[contains(normalize-space(), '" + hoursText + "')]");
        common.clickWhenClickableByLocator(hrsOption);
        Log.info("Rental hours selected: " + hoursText);
    }

    public void searchCabs() {
        common.clickWhenClickable(searchBtn);
        Log.info("Search button clicked.");
    }

    public void selectCabType(String cabType) {
        try {
            By popupOkLocator = By.xpath("//span[text()='Okay']");
            WebElement popupOkButton = common.visible(popupOkLocator);
            popupOkButton.click();
            Log.info("Popup handled by clicking 'Okay'");
        } catch (Exception e) {
            Log.info("No popup appeared after selecting cab type");
        }
        By cabTypeLocator = By.xpath("//span[normalize-space(text())='" + cabType + "']");
        common.clickWhenClickableByLocator(cabTypeLocator);
        Log.info("Cab Type selected: " + cabType);
    }

    public void selectCabModel(String modelName) {
        By modelLocator = By.xpath("//span[normalize-space(text())='" + modelName + "']");
        WebElement modelCheckbox = common.visible(modelLocator);
        common.clickWhenClickable(modelCheckbox);
        Log.info("Cab Model selected: " + modelName);
    }

    public void selectFuelType(String fuelType) {
        By fuelLocator = By.xpath("//span[normalize-space(text())='" + fuelType + "']");
        WebElement fuelCheckbox = common.visible(fuelLocator);
        common.clickWhenClickable(fuelCheckbox);
        Log.info("Fuel Type selected: " + fuelType);
    }

    public int printLowestCabPrice() {
        List<WebElement> priceElements = common.allVisibleByLocators(cabPrices);
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
            lowestPrice = Collections.min(prices);
            Log.info("Lowest cab price: " + lowestPrice);
        } else {
            Log.info("No cab prices found.");
        }
        return lowestPrice;
    }

    public List<String[]> getCabListData() {
        List<String[]> cabDataList = new ArrayList<>();
        List<WebElement> cards = common.allVisibleByLocators(cabCards);
        List<WebElement> names = common.allVisibleByLocators(cabNames);
        List<WebElement> prices = common.allVisibleByLocators(cabPrices);
        List<WebElement> ratings = common.allVisibleByLocators(cabRating);
        common.allVisibleByLocators(cabCards);
        for (int i = 0; i < cards.size(); i++) {
            String name = names.get(i).getText();
            String priceText = prices.get(i).getText().replaceAll("[^0-9]", "");
            String rating = ratings.get(i).getText();
            Log.info("Cab " + (i + 1) + ": Name = " + name + " | Price = " + priceText + " | Rating = " + rating);
            cabDataList.add(new String[]{name, priceText, rating});
        }
        return cabDataList;
    }

    public void selectLowestCab() {
        List<WebElement> cards = common.allVisibleByLocators(cabCards);
        List<WebElement> prices = common.allVisibleByLocators(cabPrices);
        List<WebElement> buttons = common.allVisibleByLocators(selectBtns);
        int lowestPrice = printLowestCabPrice();
        for (int i = 0; i < cards.size(); i++) {
            String priceText = prices.get(i).getText().replaceAll("[^0-9]", "");
            if (!priceText.isEmpty()) {
                int price = Integer.parseInt(priceText);
                if (price == lowestPrice) {
                    WebElement selectBtn = buttons.get(i);
                    common.safeClickToWebElement(selectBtn);
                    Log.info("Clicked Select Cab for price: " + lowestPrice);
                    return;
                }
            }
        }
        Log.info("Lowest cab card not found.");
    }

//Sandesh
    public List<String> getAvailableCarTypes() throws InterruptedException {
        WebElement firstEle = wait.until(ExpectedConditions.visibilityOfElementLocated(first));

        WebElement secEle = wait.until(ExpectedConditions.visibilityOfElementLocated(second));

        WebElement thirdEle = wait.until(ExpectedConditions.visibilityOfElementLocated(third));

        List<String> eleList = new ArrayList<>();
        eleList.add(firstEle.getText());
        eleList.add(secEle.getText());
        eleList.add(thirdEle.getText());

        return eleList;

    }

    // Validate the date displayed on UI is same as expected
    public boolean validateDate(String expectedDate) {
        WebElement dateElement = common.visible(cabDate);
        String uiDate = dateElement.getText().trim();
        DateTimeFormatter uiFormatter = new DateTimeFormatterBuilder()
                .appendPattern("d MMM, h:mm a")
                .parseDefaulting(ChronoField.YEAR, 2026)
                .toFormatter(Locale.ENGLISH);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("EEE MMM d yyyy", Locale.ENGLISH);
        LocalDateTime uiParsed = LocalDateTime.parse(uiDate, uiFormatter);
        LocalDate expectedParsed = LocalDate.parse(expectedDate, expectedFormatter);
        boolean result = uiParsed.toLocalDate().equals(expectedParsed);
        Log.info("Validation " + (result ? "PASSED" : "FAILED") + " → Expected: " + expectedParsed + " | Actual: " + uiParsed.toLocalDate());
        return result;
    }

    public void enterTravelerDetails(String fullName, String optionText, String mobileNumber, String email) {
        common.scrollClearType(travelerNameField, fullName);
        common.clickWhenClickable(travelerGenderField);
        By optionLocator = By.xpath("//span[contains(text(),'" + optionText + "')]");
        common.safeClick(optionLocator);
        common.scrollClearType(travelerMobileField, mobileNumber);
        common.scrollClearType(travelerEmailField, email);
        System.out.println("Traveler details entered: " + fullName + " | " + mobileNumber + " | " + email);
    }

    public boolean selectSpecialRequestAndValidatePrice(String requestName) {
        By specialRequest = By.xpath("//span[text()='" + requestName + "']");
        String priceBeforeStr = common.visible(priceTag).getText();
        int priceBefore = Integer.parseInt(priceBeforeStr.replaceAll("[^0-9]", ""));
        common.safeClick(specialRequest);
        Log.info("Selected special request: " + requestName);
        common.waitForPriceToIncrease(priceTag, priceBefore);
        String priceAfterStr = common.visible(priceTag).getText();
        int priceAfter = Integer.parseInt(priceAfterStr.replaceAll("[^0-9]", ""));
        Log.info("Price before " + requestName + ": " + priceBefore);
        Log.info("Price after " + requestName + ": " + priceAfter);
        return priceAfter > priceBefore;
    }
}