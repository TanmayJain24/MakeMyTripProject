package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CabBookingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By cabsBtn = By.xpath("//span[text()='Cabs']");
    private By fromLocator = By.xpath("//label[@for='fromCity']");
    private By fromCityInput = By.xpath("//input[@placeholder='From']");
    private By toCityInput = By.xpath("//input[@placeholder='To']");
    private By departureLabel = By.xpath("//label[@for='departure']");
    private By calendarMonths = By.className("DayPicker-Months");
    private By returnLabel = By.xpath("//label[@for='return']");
    private By hourDropdown = By.className("newTimeSlotHrUl");
    private By minuteDropdown = By.className("newTimeSlotMinUl");
    private By meridianDropdown = By.className("newTimeSlotMerUl");
    private By pickupApplyBtn = By.xpath("//div[@class='applyBtn']");
    private By searchButton = By.xpath("//a[text()='Search']");
    private By cabPrices = By.xpath("//span[@class='cabDetailsCard_price__SHN6W']");

    public CabBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Navigate to Cabs page
    public void openCabsPage() {
        wait.until(ExpectedConditions.elementToBeClickable(cabsBtn)).click();
        System.out.println("Navigated to Cabs page.");
    }

    // Select From city with dropdown
    public void selectFromCity(String from) {
        wait.until(ExpectedConditions.elementToBeClickable(fromLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(fromCityInput)).sendKeys(from);
        By fromOptionLocator = By.xpath("//span[contains(text(),'" + from + "')]");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", fromOption);
        System.out.println("Selected From city: " + from);
    }

    // Select To city with dropdown
    public void selectToCity(String to) {
        wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).click();
        wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).sendKeys(to);
        By fromOptionLocator = By.xpath("//span[contains(text(),'" + to + "')]");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", fromOption);
        System.out.println("Selected To city: " + to);
    }

    // Pick departure date
    public void selectDepartureDate(String departureDate) {
        // Open the calendar
        wait.until(ExpectedConditions.elementToBeClickable(departureLabel)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarMonths));
        By dateLocator = By.xpath("//div[@aria-label='" + departureDate + "']");
        WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dateLocator));

        //Click on the date
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dateElement);
        System.out.println("Selected departure date: " + departureDate);
    }

    // Pick return date
    public void selectReturnDate(String returnDate) {
        // Open the calendar
        wait.until(ExpectedConditions.elementToBeClickable(returnLabel)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendarMonths));
        By dateLocator = By.xpath("//div[@aria-label='" + returnDate + "']");
        WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dateLocator));

        //Click on the date
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dateElement);
        System.out.println("Selected departure date: " + returnDate);
    }

    //Select pick-up Time
    public void selectPickupTime(String hour, String minute, String meridian) {
        // Select Hour
        WebElement hourElement = wait.until(ExpectedConditions.elementToBeClickable(hourDropdown));
        hourElement.click();
        WebElement hourOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='hrSlotItemChild'][contains(text(),'" + hour + "')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hourOption);

        // Select Minute
        WebElement minuteElement = wait.until(ExpectedConditions.elementToBeClickable(minuteDropdown));
        minuteElement.click();
        WebElement minuteOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(), '" + minute + "')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", minuteOption);

        // Select AM/PM
        WebElement meridianElement = wait.until(ExpectedConditions.elementToBeClickable(meridianDropdown));
        meridianElement.click();
        WebElement meridianOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(), '" + meridian + "')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", meridianOption);

        WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(pickupApplyBtn));
        applyButton.click();

        System.out.println("Selected pickup time: " + hour + ":" + minute + " " + meridian);
    }

    // Click Search
    public void searchCabs() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        System.out.println("Search button clicked.");
    }

    public void printLowestCabPrice() {
        // Wait until price elements are visible
        List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cabPrices));

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
        System.out.println("");
        if (!prices.isEmpty()) {
            int lowestPrice = Collections.min(prices);
            System.out.println("Lowest cab price: " + lowestPrice);
        } else {
            System.out.println("No cab prices found.");
        }
    }
}
