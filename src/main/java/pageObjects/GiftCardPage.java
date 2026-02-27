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

    private WebDriver driver;
    private WebDriverWait wait;
    private CommonCode common;


    /* -------------------- LOCATORS -------------------- */

    @FindBy(xpath = "//img[@alt='minimize']")
    private WebElement closeBtn;

    @FindBy(xpath = "//span[normalize-space()='Bus']")
    private WebElement busBtn;

    @FindBy(xpath = "//a[normalize-space()='About US']")
    private WebElement scrollGiftCard;

    @FindBy(xpath = "//a[normalize-space()='Gift Cards']")
    private WebElement giftCardLocator;

    @FindBy(xpath = "//li[.//h3[normalize-space()='Wedding Gift Card']]")
    private WebElement weddingGiftCardLocator;

    @FindBy(xpath = "//input[@name='senderName']")
    private WebElement nameLocator;

    @FindBy(xpath = "//input[@name='senderMobileNo']")
    private WebElement mobileNoLocator;

    @FindBy(xpath = "//input[@name='senderEmailId']")
    private WebElement userEmail;

    @FindBy(xpath = "//button[normalize-space()='BUY NOW']")
    private WebElement buyBtn;

    @FindBy(xpath = "//input[@id='amount']")
    private WebElement enterAmountInput;

    @FindBy(xpath = "//li[contains(text(),'₹10,000')]")
    private WebElement priceAmount;

    // TODO: verify these two once against the app and adjust if needed
    @FindBy(xpath = "//p[@class='red-text font11 append-top5']")
    private WebElement userEmailInvalid;

    @FindBy(xpath = "//span[@class='make-flex font18 black-text lato-bold append-right20']")
    private WebElement nextPage_locator;

    // Keep only one list field (remove duplicates)
    @FindBy(xpath = "//h3[@class='lato-black black-text']")
    private List<WebElement> printGiftcard;

    /* -------------------- CTORS -------------------- */

    /** Preferred: let the page object create the CommonCode with a timeout. */
    public GiftCardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.common = new CommonCode(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    /** Alternative: pass a pre-built CommonCode (if you manage it centrally). */
    public GiftCardPage(WebDriver driver, CommonCode common) {
        this.driver = driver;
        this.common = common;
        PageFactory.initElements(driver, this);
    }

    /* -------------------- ACTIONS -------------------- */

    public void openGiftCardSection() {
        // Click the primary section tab
        common.visible(busBtn).click();

        // Scroll to ‘About US’ anchor to ensure Gift Cards link is in view
        common.scrollIntoViewCenter(common.visible(scrollGiftCard));

        // Click on ‘Gift Cards’
        common.safeClickToWebElement(giftCardLocator);

        // Close the popup/minimize banner if it shows up
        try {
            common.safeClickToWebElement(closeBtn);
        } catch (TimeoutException | NoSuchElementException ignored) {
            // Popup not present—continue
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

    /**
     * Waits until amount input's value changes (e.g., after selecting preset) and returns digits only.
     */
    public String getEnteredAmount() {
        common.visible(enterAmountInput);
        String updated = common.waitValueToChange(enterAmountInput, Duration.ofSeconds(8));
        return updated == null ? "" : updated.replaceAll("[^0-9]", "");
    }

    public void userDetails(String username, String mobile, String emailId) {
        // Fill name
        common.scrollClearType(nameLocator, username);

        // Fill mobile
        common.scrollClearType(mobileNoLocator, mobile);

        // Fill email
        common.scrollClearType(userEmail, emailId);

        // Trigger validations
        common.blurActive();

        // Click BUY NOW and wait for next page marker
        common.scrollIntoViewCenter(buyBtn);
        common.safeClickToWebElement(buyBtn);
        common.visible(nextPage_locator);
    }

    /**
     * Negative flow: enter possibly invalid values, try to submit, and return a summary string.
     */
    public String userDetailsInvalid(String name, String mobile, String email) {
        // Name
        common.scrollIntoViewCenter(common.visible(nameLocator));
        common.clearAndType(nameLocator, name);

        // Mobile
        common.scrollIntoViewCenter(common.visible(mobileNoLocator));
        common.clearAndType(mobileNoLocator, mobile);

        // Email
        common.scrollIntoViewCenter(common.visible(userEmail));
        common.clearAndType(userEmail, email);

        // Fire validations
        common.blurActive();

        // Attempt submit
        common.scrollIntoViewCenter(buyBtn);
        common.safeClickToWebElement(buyBtn);

        // Read the email error message
        String emailErrorText = common.visible(userEmailInvalid).getText();

        // Build quick validation summary
        String digitsOnly = (mobile == null) ? "" : mobile.replaceAll("\\D", "");
        boolean nameHasSpace = (name != null) && name.trim().contains(" ");
        boolean emailLooksValid = (email != null) && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");

        return String.format(
                "digitsOnlyMobile=%s; nameHasSpace=%s; emailLooksValid=%s; emailError='%s'",
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