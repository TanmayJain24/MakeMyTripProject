package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.stream.Collectors;

public class HotelBookingPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final String hotelsUrl = "https://www.goibibo.com/hotels/";

    // City box: click the INPUT itself (matches the UI you see)
    private final By cityInput = By.xpath("//input[contains(@placeholder,'Area') or contains(@placeholder,'Landmark') or contains(@placeholder,'Property Name') or contains(@aria-label,'Where')]");
    private final By citySuggestions = By.xpath("//ul[@role='listbox']//li");

    // Guests/Rooms
    private final By guestsRoomsTile = By.xpath("(//*[self::p or self::span or self::div][contains(.,'Guests') or contains(.,'Rooms')]/ancestor::div[contains(@role,'button')])[1]");
    // Goibibo often uses counters instead of <select>. We’ll extract any numeric options visible in the widget.
    private final By guestsWidget = By.xpath("//*[contains(@class,'guest')]//ancestor::div[contains(@role,'dialog') or contains(@class,'open') or contains(@class,'dropdown') or contains(@class,'popover')]");
    private final By numericChips = By.xpath("//div[contains(@class,'guest')]//button|//div[contains(@class,'guest')]//*[self::span or self::div][normalize-space()][matches(normalize-space(), '^[0-9]+$')]");

    // Search + results
    private final By searchBtn = By.xpath("//button[contains(.,'Search') or contains(@data-cy,'submit')] | //a[contains(@class,'searchHotels') or contains(@class,'primaryBtn')]");
    private final By resultsMarker = By.xpath("//*[contains(.,'Filters') or contains(.,'filters') or contains(.,'Sort') or contains(.,'hotels')]");
    private final By hotelCards = By.cssSelector("[data-testid='hotelCard'], .listingRowOuter, .hotelCardListing, .HotelCard");
    private final By hotelPriceInside = By.xpath(".//*[contains(@class,'price') or contains(.,'₹')]");

    // Price filter
    private final By priceFilterLabel = By.xpath("//label[contains(.,'₹') or contains(.,'Rs') or contains(.,'Price')]");

    // Details / Back
    private final By firstHotelLink = By.cssSelector("[data-testid='hotelCard'] a, .listingRowOuter a, .hotelCardListing a, .HotelCard a");
    private final By detailsMarker = By.xpath("//*[contains(.,'Overview') or contains(.,'Rooms') or contains(.,'About')]");

    public HotelBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // ---------- overlay helpers ----------
    private void dismissOverlays() {
        // close common close-able bubbles
        try { driver.findElement(By.xpath("//span[@role='presentation' or @aria-label='Close']")).click(); } catch (Exception ignored) {}
        try { driver.findElement(By.xpath("//button[contains(.,'Accept') or contains(.,'Got it') or contains(.,'OK')]")).click(); } catch (Exception ignored) {}
        // remove tooltips/popovers that intercept clicks
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('[role=\"tooltip\"], .tooltip, [data-popper-placement]').forEach(e=>e.remove());"
            );
        } catch (Exception ignored) {}
    }

    private void robustClick(By by) {
        int tries = 0;
        while (tries++ < 4) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                try { el.click(); return; } catch (ElementClickInterceptedException ice) { /* fallthrough */ }
                try { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el); return; } catch (Exception ignore) {}
                try { new Actions(driver).moveToElement(el, 2, 2).click().perform(); return; } catch (Exception ignore) {}
                throw new ElementClickInterceptedException("intercepted");
            } catch (ElementClickInterceptedException e) {
                dismissOverlays();
            }
        }
        throw new TimeoutException("Could not click: " + by);
    }

    // ---------- public actions ----------
    public void ensureHotelsLanding() {
        if (!driver.getCurrentUrl().contains("/hotels")) driver.navigate().to(hotelsUrl);
        try { wait.until(ExpectedConditions.presenceOfElementLocated(searchBtn)); } catch (Exception ignored) {}
        dismissOverlays();
    }

    public void setCity(String city) {
        // Click input directly, then type + pick first suggestion
        robustClick(cityInput);
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(cityInput));
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
        input.sendKeys(city);
        WebElement first = wait.until(ExpectedConditions.visibilityOfElementLocated(citySuggestions));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", first);
    }

    public void search() {
        robustClick(searchBtn);
        try { wait.until(ExpectedConditions.visibilityOfElementLocated(resultsMarker)); } catch (Exception ignored) {}
    }

    public List<Integer> getVisibleHotelPrices() {
        List<WebElement> cards = driver.findElements(hotelCards);
        List<Integer> prices = new ArrayList<>();
        for (WebElement c : cards) {
            try {
                WebElement p = c.findElement(hotelPriceInside);
                String v = p.getText().replaceAll("[^0-9]", "");
                if (!v.isEmpty()) prices.add(Integer.parseInt(v));
            } catch (Exception ignored) {}
        }
        return prices;
    }

    public void applyPriceFilterContaining(String labelText) {
        WebElement opt = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(.,'" + labelText + "') or contains(.,'₹')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", opt);
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
    }

    public boolean openFirstHotelDetails() {
        try { robustClick(firstHotelLink); } catch (Exception e) { return false; }
        try { return driver.findElements(detailsMarker).size() > 0; } catch (Exception e) { return false; }
    }

    public boolean backToResults() {
        driver.navigate().back();
        try { return driver.findElements(resultsMarker).size() > 0; } catch (Exception e) { return false; }
    }

    public List<Integer> openGuestsAndGetAdultOptions() {
        // Open the Guests/Rooms widget and scrape any visible numeric chips/counters
        robustClick(guestsRoomsTile);
        try { wait.until(ExpectedConditions.visibilityOfElementLocated(guestsWidget)); } catch (Exception ignored) {}

        List<WebElement> nums = driver.findElements(numericChips);
        List<Integer> vals = nums.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(s -> s.matches("\\d+"))
                .map(Integer::parseInt)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // Try to close
        try { driver.findElement(By.xpath("//button[contains(.,'APPLY') or contains(.,'Apply') or contains(.,'Done')]")).click(); } catch (Exception ignored) {}
        return vals;
    }
}