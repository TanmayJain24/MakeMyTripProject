package pageObjects;

import org.openqa.selenium.*;
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
    }
    private final By closeBtn = By.xpath("//img[@alt='minimize']");
    private final By busBtn = By.xpath("//ul[@class='sc-1f95z5i-0 fTpLLU']//li[5]");
    private final By scrollGiftCard = By.xpath("//a[normalize-space()='About US']");
    private final By giftCardLocator = By.xpath("//a[normalize-space()='Gift Cards']");
    private final By WeddingGiftCardLocator = By.xpath("//li[.//h3[normalize-space()='Wedding Gift Card']]");
    private final By nameLocator = By.xpath("//input[@name='senderName']");
    private final By mobileNoLocator = By.xpath("//input[@name='senderMobileNo']");
    private final  By userEmail = By.xpath("//input[@name='senderEmailId']");
    private final  By buyBtn = By.xpath("//button[normalize-space()='BUY NOW']");
    private final By EnterAmountLocator = By.xpath("//input[@id='amount']");
    private final By priceAmount = By.xpath("//li[contains(text(),'₹10,000')]");
    private final  By userEmailInvalid = By.xpath("//p[@class='red-text font11 append-top5']");
    private final By nextPage_locator = By.xpath("//span[@class='make-flex font18 black-text lato-bold append-right20']");
    private final By PrintGiftcard = By.xpath("//h3[@class='lato-black black-text']");

    public void openGiftCardSection() {
        wait.until(ExpectedConditions.presenceOfElementLocated(busBtn)).click();
        WebElement giftCardSection = wait.until(ExpectedConditions.elementToBeClickable(giftCardLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement scrollToGiftCard = wait.until(ExpectedConditions.elementToBeClickable(scrollGiftCard));
        js.executeScript("arguments[0].scrollIntoView(true)", scrollToGiftCard);
        wait.until(ExpectedConditions.elementToBeClickable(giftCardSection)).click();
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
    }


    public void clickGiftCard() {
        WebElement WeddingGiftCard = wait.until(ExpectedConditions.visibilityOfElementLocated(WeddingGiftCardLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", WeddingGiftCard);
        try {
            WeddingGiftCard.click();
        } catch (Exception e) {
            System.out.println("Standard click failed, attempting JS click...");
            js.executeScript("arguments[0].click();", WeddingGiftCard);
        }


        // Method for Price Locator
    }
        public void SelectAmount(){
          WebElement priceBtn = wait.until(ExpectedConditions.elementToBeClickable(priceAmount));
          priceBtn.click();
        }

    public String getEnteredAmount() {
        String before = driver.findElement(EnterAmountLocator).getAttribute("value");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(EnterAmountLocator));
        new WebDriverWait(driver, Duration.ofSeconds(8)).until(d -> {
            String now = d.findElement(EnterAmountLocator).getAttribute("value");
            return now != null && !now.equals(before);
        });
        return input.getAttribute("value").replaceAll("[^0-9]", "");
    }

    public void userDetails() {
        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(nameLocator));
        WebElement mobileNo = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNoLocator));
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmail));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll name into view (center; avoid 'smooth' for stability)
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", name);
        wait.until(ExpectedConditions.elementToBeClickable(name));

        // Clear + type (Ctrl+A + Delete is more reliable than clear())
        name.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        name.sendKeys(Keys.DELETE);
        name.sendKeys("Tanishk Yadav");

        // Mobile
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", mobileNo);
        wait.until(ExpectedConditions.elementToBeClickable(mobileNo));
        mobileNo.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        mobileNo.sendKeys(Keys.DELETE);
        mobileNo.sendKeys("7413938042");

        // Email
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", email);
        wait.until(ExpectedConditions.elementToBeClickable(email));
        email.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        email.sendKeys(Keys.DELETE);
        email.sendKeys("tanishkyadav933@gmail.com");

        // Trigger inline validations (blur active element)
        js.executeScript("if (document.activeElement) document.activeElement.blur();");

        // Click BUY with interception fallback
        WebElement buy = wait.until(ExpectedConditions.elementToBeClickable(buyBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", buy);
        try {
            buy.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(nextPage_locator));
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", buy);
        }
    }

    public String userDetailsInvalid(String name, String mobile, String email) {
        // Wait for fields (ensures page state is ready)
        driver.navigate().back();
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameLocator));
        WebElement mobileField = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNoLocator));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmail));
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

        // Collect error message(s) AFTER triggering validation
        String emailErrorText = "";
        try {
            WebElement errMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmailInvalid));
            emailErrorText = errMsg.getText();
            System.out.println("Email error: " + emailErrorText);
        } catch (TimeoutException te) {
            // No visible email error
        }

        // Derivations you were computing
        String digitsOnly = (mobile == null) ? "" : mobile.replaceAll("\\D", "");
        boolean nameHasSpace = (name != null) && name.trim().contains(" ");
        boolean emailLooksValid = (email != null) && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");

        // Return a compact summary useful for assertions
        return String.format(
                "digitsOnlyMobile=%s; nameHasSpace=%s; emailLooksValid=%s; emailError='%s'",
                digitsOnly, nameHasSpace, emailLooksValid, emailErrorText
        );
    }
    public List<String> getGiftCardTitles() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PrintGiftcard));

        List<WebElement> cards = driver.findElements(PrintGiftcard);
        List<String> titles = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            try {
                titles.add(cards.get(i).getText().trim());
            } catch (StaleElementReferenceException e) {
                // Re-locate if stale
                WebElement card = driver.findElements(PrintGiftcard).get(i);
                titles.add(card.getText().trim());
            }
        }

        // Print
        System.out.println("Total gift cards found: " + titles.size());
        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }

        return titles;
    }
}