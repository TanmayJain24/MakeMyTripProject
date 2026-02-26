package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InsurancePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='/insurance?cmp=desktop_header']")
    private WebElement insuranceTabBtn;
    @FindBy(xpath = "//span[text()='Thailand']")
    private WebElement thailandCheckbox;
    @FindBy(xpath = "//button[@class='TravellingToWidgetstyle__CounterBtn-sc-56hbau-14']")
    private WebElement travellerAddBtn;
    @FindBy(xpath = "//button[@class='LandingButtonstyle__LandingButtonStyle-sc-1ldh0sr-0 glaPGB']")
    private WebElement viewPlansBtn;

    public InsurancePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void clickInsuranceTab() {
        wait.until(ExpectedConditions.elementToBeClickable(insuranceTabBtn)).click();
        System.out.println("Navigated to Insurance section.");
    }

    public void selectThailand() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(thailandCheckbox));
        if (!element.isSelected()) {
            element.click();
        }
        System.out.println("Thailand checkbox selected.");
    }

    public void addOneTraveller() {
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(travellerAddBtn));
        addBtn.click();
        System.out.println("Added +1 traveller.");
    }

    public void clickViewPlans() {
        wait.until(ExpectedConditions.elementToBeClickable(viewPlansBtn)).click();
        System.out.println("Clicked on View Plans.");
    }

    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        System.out.println("Scrolled to the end of the page.");
    }
}