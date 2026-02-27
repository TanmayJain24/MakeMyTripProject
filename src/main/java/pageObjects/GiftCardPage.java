package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GiftCardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public GiftCardPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//img[@alt='minimize']")
    private WebElement closeBtn;
    @FindBy(xpath = "//span[normalize-space()='Bus']")
    private WebElement busBtn;
    @FindBy(xpath = "//a[normalize-space()='About US']")
    private WebElement scrollGiftCard;
    @FindBy(xpath = "//a[normalize-space()='Gift Cards']")
    private WebElement giftCardLocator;
    @FindBy(xpath = "//li[.//h3[normalize-space()='Wedding Gift Card']]")
    private WebElement WeddingGiftCardLocator;
    @FindBy(xpath = "//input[@name='senderName']")
    private WebElement nameLocator;
    @FindBy(xpath = "//input[@name='senderMobileNo']")
    private WebElement mobileNoLocator;
    @FindBy(xpath = "//input[@name='senderEmailId']")
    private WebElement userEmail;
    @FindBy(xpath = "//button[normalize-space()='BUY NOW']")
    private WebElement buyBtn;
    @FindBy(xpath = "//input[@id='amount']")
    private WebElement EnterAmountLocator;
    @FindBy(xpath = "//li[contains(text(),'₹10,000')]")
    private WebElement priceAmount;
    @FindBy(xpath = "//p[@class='red-text font11 append-top5']")
    private WebElement userEmailInvalid;
    @FindBy(xpath = "//span[@class='make-flex font18 black-text lato-bold append-right20']")
    private WebElement nextPage_locator;
    @FindBy(xpath = "//h3[@class='lato-black black-text']")
    private List<WebElement> PrintGiftcard;
    @FindBy(xpath = "//input[@id='amount']")
    private WebElement enterAmountInput;
    @FindBy(xpath = "//h3[@class='lato-black black-text']")
    private List<WebElement> printGiftcard;

    public void openGiftCardSection() {
        wait.until(ExpectedConditions.visibilityOf(busBtn)).click();
        WebElement giftCardSection = wait.until(ExpectedConditions.elementToBeClickable(giftCardLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement scrollToGiftCard = wait.until(ExpectedConditions.elementToBeClickable(scrollGiftCard));
        js.executeScript("arguments[0].scrollIntoView(true)", scrollToGiftCard);
        wait.until(ExpectedConditions.elementToBeClickable(giftCardSection)).click();
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
    }

    public void clickGiftCard() {
        WebElement WeddingGiftCard = wait.until(ExpectedConditions.visibilityOf(WeddingGiftCardLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", WeddingGiftCard);
        try {
            WeddingGiftCard.click();
        } catch (Exception e) {
            System.out.println("Standard click failed, attempting JS click...");
            js.executeScript("arguments[0].click();", WeddingGiftCard);
        }
    }

    public void SelectAmount(){
        WebElement priceBtn = wait.until(ExpectedConditions.elementToBeClickable(priceAmount));
        priceBtn.click();
    }

    public String getEnteredAmount() {
        // Ensure the input is visible and grab the initial value
        WebElement input = wait.until(ExpectedConditions.visibilityOf(enterAmountInput));
        String before = input.getAttribute("value");

        // Wait until the value becomes non-null and different from 'before'
        new WebDriverWait(driver, Duration.ofSeconds(8)).until(drv -> {
            String now = enterAmountInput.getAttribute("value");
            return now != null && !now.equals(before);
        });

        // Return only digits
        return enterAmountInput.getAttribute("value").replaceAll("[^0-9]", "");
    }

    public void userDetails(String username, String mobile, String emailId) {
        WebElement name = wait.until(ExpectedConditions.visibilityOf(nameLocator));
        WebElement mobileNo = wait.until(ExpectedConditions.visibilityOf(mobileNoLocator));
        WebElement email = wait.until(ExpectedConditions.visibilityOf(userEmail));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll name into view (center; avoid 'smooth' for stability)
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", name);
        wait.until(ExpectedConditions.elementToBeClickable(name));

        // Clear + type (Ctrl+A + Delete is more reliable than clear())
        name.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        name.sendKeys(Keys.DELETE);
        name.sendKeys(username);

        // Mobile
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", mobileNo);
        wait.until(ExpectedConditions.elementToBeClickable(mobileNo));
        mobileNo.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        mobileNo.sendKeys(Keys.DELETE);
        mobileNo.sendKeys(mobile);

        // Email
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", email);
        wait.until(ExpectedConditions.elementToBeClickable(email));
        email.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        email.sendKeys(Keys.DELETE);
        email.sendKeys(emailId);

        // Trigger inline validations (blur active element)
        js.executeScript("if (document.activeElement) document.activeElement.blur();");

        // Click BUY with interception fallback
        WebElement buy = wait.until(ExpectedConditions.elementToBeClickable(buyBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", buy);
        try {
            buy.click();
            wait.until(ExpectedConditions.visibilityOf(nextPage_locator));
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", buy);
        }
    }

    public String userDetailsInvalid(String name, String mobile, String email) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOf(nameLocator));
        WebElement mobileField = wait.until(ExpectedConditions.visibilityOf(mobileNoLocator));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOf(userEmail));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", nameField);
        wait.until(ExpectedConditions.elementToBeClickable(nameField));
        nameField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        nameField.sendKeys(Keys.DELETE);
        if (name != null) nameField.sendKeys(name);

        // Fill mobile
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", mobileField);
        wait.until(ExpectedConditions.elementToBeClickable(mobileField));
        mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        mobileField.sendKeys(Keys.DELETE);
        if (mobile != null) mobileField.sendKeys(mobile);

        // Fill email
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", emailField);
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        emailField.sendKeys(Keys.DELETE);
        if (email != null) emailField.sendKeys(email);
        // Trigger validations (blur + submit)
        js.executeScript("if (document.activeElement) document.activeElement.blur();");
        WebElement buy = wait.until(ExpectedConditions.elementToBeClickable(buyBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", buy);
        try {
            buy.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", buy);
        }
        // Collect error message AFTER triggering validation
        String emailErrorText = "";
        WebElement errMsg = wait.until(ExpectedConditions.visibilityOf(userEmailInvalid));
        emailErrorText = errMsg.getText();
        System.out.println("Email error: " + emailErrorText);
        String digitsOnly = (mobile == null) ? "" : mobile.replaceAll("\\D", "");
        boolean nameHasSpace = (name != null) && name.trim().contains(" ");
        boolean emailLooksValid = (email != null) && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");
        // Compact summary useful for assertions
        return String.format(
                "digitsOnlyMobile=%s; nameHasSpace=%s; emailLooksValid=%s; emailError='%s'",
                digitsOnly, nameHasSpace, emailLooksValid, emailErrorText
        );
    }

    public List<String> getGiftCardTitles() {
        wait.until(ExpectedConditions.visibilityOfAllElements(printGiftcard));
        List<WebElement> cards = new ArrayList<>(printGiftcard);
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            try {
                titles.add(cards.get(i).getText().trim());
            } catch (StaleElementReferenceException e) {
                WebElement stale = cards.get(i);
                wait.until(ExpectedConditions.stalenessOf(stale));
                WebElement refetched = printGiftcard.get(i);
                titles.add(refetched.getText().trim());
            }
        }
        System.out.println("Total gift cards found: " + titles.size());
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }
        return titles;
    }
}