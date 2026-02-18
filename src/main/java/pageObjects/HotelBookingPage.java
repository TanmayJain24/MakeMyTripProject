package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class HotelBookingPage {
    private WebDriver driver;

    // Locators
    private By hotelSection = By.id("hotelSection");
    private By destinationField = By.id("destination");
    private By checkInDate = By.id("checkIn");
    private By checkOutDate = By.id("checkOut");
    private By searchButton = By.id("searchHotels");
    private By adultDropdown = By.id("adults");
    private By childDropdown = By.id("children");
    private By hotelNames = By.cssSelector(".hotelName");
    private By hotelPrices = By.cssSelector(".hotelPrice");

    public HotelBookingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHotelBooking() {
        driver.findElement(hotelSection).click();
    }

    public void searchHotels(String destination, String checkIn, String checkOut) {
        driver.findElement(destinationField).sendKeys(destination);
        driver.findElement(checkInDate).sendKeys(checkIn);
        driver.findElement(checkOutDate).sendKeys(checkOut);
        driver.findElement(searchButton).click();
    }

    public List<String> getHotelNames() {
        List<WebElement> hotels = driver.findElements(hotelNames);
        List<String> names = new ArrayList<>();
        for (WebElement hotel : hotels) {
            names.add(hotel.getText());
        }
        return names;
    }

    public List<Integer> getHotelPrices() {
        List<WebElement> prices = driver.findElements(hotelPrices);
        List<Integer> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            String priceText = price.getText().replaceAll("[^0-9]", "");
            if (!priceText.isEmpty()) {
                priceList.add(Integer.parseInt(priceText));
            }
        }
        return priceList;
    }
}