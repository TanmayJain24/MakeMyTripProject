package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonCode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GiftCardPage {
    WebDriver driver;
    WebDriverWait wait;
    CommonCode common;

    @FindBy(xpath = "//img[@alt='minimize']")
    WebElement closeBtn;

    @FindBy(xpath = "//span[normalize-space()='Bus']")
    WebElement busBtn;

    @FindBy(xpath = "//a[normalize-space()='About US']")
    WebElement scrollGiftCard;

    @FindBy(xpath = "//a[normalize-space()='Gift Cards']")
    WebElement giftCardLocator;

    @FindBy(xpath = "//li[.//h3[normalize-space()='Wedding Gift Card']]")
    WebElement weddingGiftCardLocator;

    @FindBy(xpath = "//input[@name='senderName']")
    WebElement nameLocator;

    @FindBy(xpath = "//input[@name='senderMobileNo']")
    WebElement mobileNoLocator;

    @FindBy(xpath = "//input[@name='senderEmailId']")
    WebElement userEmail;

    @FindBy(xpath = "//button[normalize-space()='BUY NOW']")
    WebElement buyBtn;

    @FindBy(xpath = "//input[@id='amount']")
    WebElement enterAmountInput;

    @FindBy(xpath = "//li[contains(text(),'₹10,000')]")
    WebElement priceAmount;

    @FindBy(xpath = "//p[@class='red-text font11 append-top5']")
    WebElement userEmailInvalid;

    @FindBy(xpath = "//span[@class='make-flex font18 black-text lato-bold append-right20']")
    WebElement nextPage_locator;

    @FindBy(xpath = "//h3[@class='lato-black black-text']")
    List<WebElement> printGiftcard;

    public GiftCardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.common = new CommonCode(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void openGiftCardSection() {
        common.visible(busBtn).click();
        common.scrollIntoViewCenter(common.visible(scrollGiftCard));
        common.safeClickToWebElement(giftCardLocator);
        try {
            common.safeClickToWebElement(closeBtn);
        } catch (TimeoutException | NoSuchElementException ignored) {
        }
    }

    public void clickGiftCard() {
        WebElement weddingCard = common.visible(weddingGiftCardLocator);
        common.scrollIntoViewCenter(weddingCard);
        common.safeClickToWebElement(weddingCard);
    }

    public void selectAmount() {
        common.safeClickToWebElement(priceAmount);
    }

    public String getEnteredAmount() {
        common.visible(enterAmountInput);
        String updated = common.waitValueToChange(enterAmountInput, Duration.ofSeconds(8));
        return updated == null ? "" : updated.replaceAll("[^0-9]", "");
    }

    public void userDetails(String username, String mobile, String emailId) {
        common.scrollClearType(nameLocator, username);
        common.scrollClearType(mobileNoLocator, mobile);
        common.scrollClearType(userEmail, emailId);
        common.blurActive();
        common.scrollIntoViewCenter(buyBtn);
        common.safeClickToWebElement(buyBtn);
        common.visible(nextPage_locator);
    }

    public String userDetailsInvalid(String name, String mobile, String email) {
        common.scrollIntoViewCenter(common.visible(nameLocator));
        common.clearAndType(nameLocator, name);
        common.scrollIntoViewCenter(common.visible(mobileNoLocator));
        common.clearAndType(mobileNoLocator, mobile);
        common.scrollIntoViewCenter(common.visible(userEmail));
        common.clearAndType(userEmail, email);
        common.blurActive();
        common.scrollIntoViewCenter(buyBtn);
        common.safeClickToWebElement(buyBtn);
        String emailErrorText = common.visible(userEmailInvalid).getText();
        String digitsOnly = (mobile == null) ? "" : mobile.replaceAll("\\D", "");
        boolean nameHasSpace = (name != null) && name.trim().contains(" ");
        boolean emailLooksValid = (email != null) && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");
        return String.format("digitsOnlyMobile=%s; nameHasSpace=%s; emailLooksValid=%s; emailError='%s'",
                digitsOnly, nameHasSpace, emailLooksValid, emailErrorText
        );
    }

    public List<String> getGiftCardTitles() {
        common.allVisible(printGiftcard);
        List<String> titles = common.getTextsHandlingStale(printGiftcard);
        System.out.println("Total gift cards found: " + titles.size());
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }
        return new ArrayList<>(titles);
    }
}