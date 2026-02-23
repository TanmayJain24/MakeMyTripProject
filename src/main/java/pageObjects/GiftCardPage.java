//package pageObjects;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//
//public class GiftCardPage {
//    private WebDriver driver;
//
//    // Locators
//    private By giftCardSection = By.id("giftCardSection");
//    private By nameField = By.id("name");
//    private By emailField = By.id("email");
//    private By amountDropdown = By.id("amount");
//    private By submitButton = By.id("submitGiftCard");
//
//    public GiftCardPage(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    public void openGiftCardSection() {
//        driver.findElement(giftCardSection).click();
//    }
//
//    public void fillGiftCardDetails(String name, String email, String amount) {
//        driver.findElement(nameField).sendKeys(name);
//        driver.findElement(emailField).sendKeys(email);
//        driver.findElement(amountDropdown).sendKeys(amount);
//        driver.findElement(submitButton).click();
//    }
//}



package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class GiftCardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Top-level navigation & Group Gifting tab
    private final By giftNav = By.xpath("//a[contains(@href,'gift') or contains(.,'Gift Card') or contains(.,'Giftcards')]");
    private final By groupGiftingTab = By.xpath("//*[contains(.,'Group Gifting')][self::a or self::div or self::span]");

    // Recipient row fields (target the LAST available set so we can add multiple)
    private final By nameFieldLast   = By.xpath("(//input[contains(@placeholder,'Name') and not(@type='email')])[last()]");
    private final By emailFieldLast  = By.xpath("(//input[@type='email' or contains(@placeholder,'Email')])[last()]");
    private final By amountFieldLast = By.xpath("(//input[contains(@placeholder,'Amount') or @name='amount'])[last()]");

    // Add more recipients / proceed
    private final By addAnotherBtn = By.xpath("//button[normalize-space()[contains(.,'Add')] and (contains(.,'Another') or contains(.,'Recipient') or contains(.,'More'))]");
    private final By proceedBtn    = By.xpath("//button[contains(.,'Proceed') or contains(.,'Continue') or contains(.,'NEXT') or contains(.,'Next')]");

    // Review/Payment marker after proceed
    private final By reviewMarker = By.xpath("//*[contains(.,'Review') or contains(.,'Summary') or contains(.,'Payment') or contains(.,'Checkout')]");

    // Optional: sometimes there is a generic “Agree/Accept” checkbox
    private final By agreeCheckbox = By.xpath("//input[@type='checkbox' and (contains(@name,'agree') or contains(@id,'agree'))]");

    public GiftCardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openGiftCardsAndGroupGifting() {
        wait.until(ExpectedConditions.elementToBeClickable(giftNav)).click();
        try { wait.until(ExpectedConditions.elementToBeClickable(groupGiftingTab)).click(); } catch (Exception ignored) {}
    }

    public void addRecipient(String name, String email, String amount) {
        WebElement nf = wait.until(ExpectedConditions.visibilityOfElementLocated(nameFieldLast));
        WebElement ef = wait.until(ExpectedConditions.visibilityOfElementLocated(emailFieldLast));
        WebElement af = wait.until(ExpectedConditions.visibilityOfElementLocated(amountFieldLast));
        nf.clear(); nf.sendKeys(name);
        ef.clear(); ef.sendKeys(email);
        af.clear(); af.sendKeys(amount);
    }

    public void addAnotherRecipientRow() {
        try { wait.until(ExpectedConditions.elementToBeClickable(addAnotherBtn)).click(); } catch (Exception ignored) {}
    }

    public boolean proceedToReviewOrPayment() {
        try {
            List<WebElement> agree = driver.findElements(agreeCheckbox);
            if (!agree.isEmpty() && !agree.get(0).isSelected()) agree.get(0).click();
        } catch (Exception ignored) {}

        try { wait.until(ExpectedConditions.elementToBeClickable(proceedBtn)).click(); } catch (Exception ignored) {}
        try { return driver.findElements(reviewMarker).size() > 0; } catch (Exception e) { return false; }
    }
}
