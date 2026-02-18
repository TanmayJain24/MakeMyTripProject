package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FlightBookingPage {
    private WebDriver driver;

    // Locators
    private By flightSection = By.id("flightSection");
    private By fromCity = By.id("fromCity");
    private By toCity = By.id("toCity");
    private By departureDate = By.id("departureDate");
    private By searchButton = By.id("searchFlights");
    private By cabinClassDropdown = By.id("cabinClass");
    private By flightResults = By.cssSelector(".flightResult");

    public FlightBookingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openFlightBooking() {
        driver.findElement(flightSection).click();
    }

    public void searchFlights(String from, String to, String date) {
        driver.findElement(fromCity).sendKeys(from);
        driver.findElement(toCity).sendKeys(to);
        driver.findElement(departureDate).sendKeys(date);
        driver.findElement(searchButton).click();
    }

    public void selectCabinClass(String cabinClass) {
        driver.findElement(cabinClassDropdown).sendKeys(cabinClass);
    }

    public String getFlightResults() {
        return driver.findElement(flightResults).getText();
    }
}