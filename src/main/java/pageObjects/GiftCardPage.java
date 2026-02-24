package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GiftCardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public GiftCardPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    private final By flightsBtn = By.xpath("//span[text()='Flights']");
    private final By giftCardLocator = By.xpath("//li[@data-cy='tertiaryRowItem_Gift Cards']");
    private final By WeddingGiftCardLocator = By.xpath("//li[.//h3[normalize-space()='Wedding Gift Card']]");
    private final By nameLocator = By.xpath("//input[@name='senderName']");
    private  final By mobileNoLocator = By.xpath("//input[@name='senderMobileNo']");
    private final  By userEmail = By.xpath("//input[@name='senderEmailId']");
    private final  By buyBtn = By.xpath("//button[normalize-space()='BUY NOW']");
    private final By EnterAmountLocator = By.xpath("//input[@id='amount']");
    private final By priceAmount = By.xpath("//li[contains(text(),'₹10,000')]");
    private final  By userEmailInvalid = By.cssSelector("div[class='form__field__wrap'] p[class='red-text font11 append-top5']");


    public void openGiftCardSection() {
        wait.until(ExpectedConditions.elementToBeClickable(flightsBtn)).click();
        WebElement giftCardSection = wait.until(ExpectedConditions.elementToBeClickable(giftCardLocator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", giftCardSection);
        wait.until(ExpectedConditions.elementToBeClickable(giftCardSection)).click();
    }

    // Put this in your GiftCardPage or a common utils class
    public void forceCloseLoginModalAndUnlockPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1) Remove the modal wrapper by data-id (from your HTML)
        try {
            WebElement modal = driver.findElement(By.cssSelector("[data-id='dweb-modal']"));
            js.executeScript("if(arguments[0]){arguments[0].remove();}", modal);
            System.out.println("Removed modal [data-id='dweb-modal'].");
        } catch (NoSuchElementException ignore) {
            System.out.println("Modal not found; maybe already gone.");
        }

        // 2) Remove any backdrops/overlays that dim the screen
        try {
            js.executeScript(
                    "document.querySelectorAll(\"[class*='overlay'],[class*='backdrop'],[class*='modal-backdrop']\").forEach(e=>e.remove());"
                            + "document.querySelectorAll(\".sc-guJBdh,.sc-jaXxmE,.sc-hwdzOV\").forEach(e=>{e.style.display='none'; e.remove();});"
            );
            System.out.println("Removed common overlays/backdrops.");
        } catch (Exception ignore) {}

        // 3) Restore body scrolling and pointer events
        try {
            js.executeScript(
                    "document.body.style.overflow='auto';" +
                            "document.documentElement.style.overflow='auto';" +
                            "document.body.style.pointerEvents='auto';" +
                            "document.documentElement.style.pointerEvents='auto';" +
                            // Remove any framework-specific 'modal-open' classes that freeze the page
                            "document.body.classList.remove('modal-open','no-scroll','overlay-open');"
            );
            System.out.println("Re-enabled body scroll and pointer events.");
        } catch (Exception ignore) {}

        // 4) Clear aria-hidden on the main app if set (some modals set this on the rest of the page)
        try {
            js.executeScript(
                    "document.querySelectorAll('[aria-hidden=\"true\"]').forEach(e=>{" +
                            " if(!e.closest('[data-id=\"dweb-modal\"]')){ e.setAttribute('aria-hidden','false'); }" +
                            "});"
            );
            System.out.println("Cleared aria-hidden on main content.");
        } catch (Exception ignore) {}

        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(WeddingGiftCardLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);

        // 5) Small wait for layout to settle
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }

    public void clickGiftCard() {
        forceCloseLoginModalAndUnlockPage();
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

    public void userDetails() throws InterruptedException {

        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(nameLocator));
        WebElement MobileNo = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNoLocator));
        WebElement Email = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmail));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", name);
        wait.until(ExpectedConditions.elementToBeClickable(Email));
        name.sendKeys("Tanishk Yadav");


        MobileNo.sendKeys("7413938042");


        Email.sendKeys("tanishkyadav933@gmail.com");

        WebElement Buy = wait.until(ExpectedConditions.elementToBeClickable(buyBtn));
        Buy.click();

    }

    // import org.testng.Assert;  // make sure this import exists

    public String userDetailsInvalid(String name, String mobile, String email) throws InterruptedException {

        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameLocator));
        WebElement mobileField = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNoLocator));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmail));

        // Fill fields
        nameField.clear();
        nameField.sendKeys(name);

        mobileField.clear();
        mobileField.sendKeys(mobile);

        emailField.clear();
        emailField.sendKeys(email);


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", emailField);


        boolean nameHasSpace = name != null && name.trim().contains(" ");

        String digitsOnly = mobile == null ? "" : mobile.replaceAll("\\D", ""); // keep only digits


        String simpleValidEmailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$";
        boolean emailLooksValid = email != null && email.matches(simpleValidEmailRegex);
        WebElement errMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmailInvalid));
        System.out.println(errMsg.getText());

        // Try clicking BUY NOW (to observe UI behavior even in invalid case)
        WebElement buy = wait.until(ExpectedConditions.elementToBeClickable(buyBtn));
        buy.click();
        return digitsOnly;
    }

}
