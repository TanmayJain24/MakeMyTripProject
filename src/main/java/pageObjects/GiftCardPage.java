package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GiftCardPage {
    private WebDriver driver;

    // Locators
    private By giftCardSection = By.id("giftCardSection");
    private By nameField = By.id("name");
    private By emailField = By.id("email");
    private By amountDropdown = By.id("amount");
    private By submitButton = By.id("submitGiftCard");

    public GiftCardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openGiftCardSection() {
        driver.findElement(giftCardSection).click();
    }

    public void fillGiftCardDetails(String name, String email, String amount) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(amountDropdown).sendKeys(amount);
        driver.findElement(submitButton).click();
    }
}
