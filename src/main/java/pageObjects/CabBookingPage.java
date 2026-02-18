package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CabBookingPage {
    private WebDriver driver;

    // Locators
    private By fromCity = By.id("fromCity");
    private By toCity = By.id("toCity");
    private By datePicker = By.id("departure");
    private By timePicker = By.id("timePicker");
    private By carTypeDropdown = By.id("carType");
    private By searchButton = By.id("searchCabs");
    private By lowestCharge = By.cssSelector(".lowestCharge");

    public CabBookingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterTripDetails(String from, String to, String date, String time, String carType) {
        driver.findElement(fromCity).sendKeys(from);
        driver.findElement(toCity).sendKeys(to);
        driver.findElement(datePicker).sendKeys(date);
        driver.findElement(timePicker).sendKeys(time);
        driver.findElement(carTypeDropdown).sendKeys(carType);
    }

    public void searchCabs() {
        driver.findElement(searchButton).click();
    }

    public String getLowestCharges() {
        WebElement chargeElement = driver.findElement(lowestCharge);
        return chargeElement.getText();
    }
}