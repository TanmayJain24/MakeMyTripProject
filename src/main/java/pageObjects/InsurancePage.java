package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.CommonCode;
import utilities.Log; // Importing your custom Log class
import java.time.Duration;

public class InsurancePage {
    private final CommonCode common;

    @FindBy(xpath = "//a[@href='/insurance?cmp=desktop_header']")
    private WebElement insuranceTabBtn;

    @FindBy(xpath = "//span[text()='Thailand']")
    private WebElement thailandCheckbox;

    @FindBy(xpath = "//button[@class='TravellingToWidgetstyle__CounterBtn-sc-56hbau-14']")
    private WebElement travellerAddBtn;

    @FindBy(xpath = "//button[@class='LandingButtonstyle__LandingButtonStyle-sc-1ldh0sr-0 glaPGB']")
    private WebElement viewPlansBtn;

    @FindBy(xpath = "//div[@data-test-id='InsurancePlansComp-InsurancePlansCompStyle']")
    private WebElement insuranceCards;

    public InsurancePage(WebDriver driver, Duration timeout) {
        this.common = new CommonCode(driver, timeout);
        PageFactory.initElements(driver, this);
        Log.info("Insurance Page initialized.");
    }
    public void clickInsuranceTab() {
        Log.info("Navigating to Insurance section via Header Tab.");
        common.safeClickToWebElement(insuranceTabBtn);
    }
    public void selectThailand() {
        Log.info("Selecting 'Thailand' from the destination list.");
        common.safeClickToWebElement(thailandCheckbox);
    }
    public void addOneTraveller() {
        Log.info("Incrementing traveller count by 1.");
        common.clickWhenClickable(travellerAddBtn);
    }
    public void clickViewPlans() {
        Log.info("Clicking the 'View Plans' button.");
        common.safeClickToWebElement(viewPlansBtn);
    }
    public void scrollToBottom() {
        Log.info("Scrolling through insurance plans to ensure visibility.");
        common.visible(insuranceCards);
        common.scrollToBottom();
        common.visible(insuranceCards);
        Log.info("Successfully reached the end of the plans list.");
    }
}