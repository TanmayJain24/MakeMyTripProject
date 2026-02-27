package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommonCode {
    private final WebDriver driver;
    private final Duration timeout;
    private final JavascriptExecutor js;
    private final boolean isMacLike;

    public CommonCode(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
        this.js = (JavascriptExecutor) driver;
        this.isMacLike = System.getProperty("os.name", "")
                .toLowerCase(Locale.ROOT)
                .contains("mac");
    }

    /* -------------------- CORE WAITERS -------------------- */

    public WebDriverWait getWait() {
        return new WebDriverWait(driver, timeout);
    }

    /** Wait until an element located by locator is visible. */
    public WebElement visible(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait until a specific WebElement is visible and return it. */
    public WebElement visible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    /** Wait until element is clickable then click. */
    public void clickWhenClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /** Wait until a WebElement is clickable and return it. */
    public WebElement waitUntilClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /** Wait all elements are visible; useful for lists. */
    public List<WebElement> allVisible(List<WebElement> elements) {
        return getWait().until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    /** Page ready state == 'complete'. */
    public void pageReady() {
        getWait().until(drv ->
                "complete".equals(js.executeScript("return document.readyState"))
        );
    }

    /* -------------------- SAFE CLICK HELPERS -------------------- */

    /**
     * Safe click for a By locator with retries.
     * Scrolls into view (center), tries native click, then JS click fallback.
     */
    public void safeClick(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement el = getWait().until(ExpectedConditions.elementToBeClickable(locator));
                scrollIntoViewCenter(el);
                el.click();
                return;
            } catch (ElementClickInterceptedException e) {
                try {
                    WebElement el = driver.findElement(locator);
                    scrollIntoViewCenter(el);
                    el.click();
                    return;
                } catch (Exception ignored) { /* will retry */ }
            } catch (StaleElementReferenceException ignored) { /* retry */ }
            catch (Exception e) {
                try {
                    WebElement fresh = getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
                    scrollIntoViewCenter(fresh);
                    jsClick(fresh);
                    return;
                } catch (StaleElementReferenceException ignored) { /* retry */ }
            }
            attempts++;
            sleep(200);
        }
        throw new TimeoutException("safeClick failed after retries for locator: " + locator);
    }

    /**
     * Safe click for a WebElement with retries.
     * Scrolls into view (center), tries native click, then JS click fallback.
     */
    public void safeClickToWebElement(WebElement element) {
        WebDriverWait wait = getWait();
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(element));
                scrollIntoViewCenter(el);
                el.click();
                return;
            } catch (StaleElementReferenceException ignored) {
                // element went stale; retry
            } catch (ElementClickInterceptedException e) {
                scrollIntoViewCenter(element);
            } catch (JavascriptException ignored) {
                // ignore and retry
            }
            attempts++;
            sleep(200);
        }
        try {
            scrollIntoViewCenter(element);
            jsClick(element);
        } catch (Exception ex) {
            throw new TimeoutException("safeClick(WebElement) failed after retries.", ex);
        }
    }

    /* -------------------- TYPE/CLEAR HELPERS -------------------- */

    /**
     * Simple enterText using element.clear(); good for plain inputs.
     * For complex/controlled inputs, prefer clearWithShortcut / clearAndType.
     */
    public void enterText(WebElement element, String text) {
        WebElement el = getWait().until(ExpectedConditions.visibilityOf(element));
        scrollIntoViewCenter(el);
        el.clear();
        el.sendKeys(text);
    }

    /** OS‑aware Select All chord (⌘+A on macOS, Ctrl+A otherwise). */
    public CharSequence selectAllChord() {
        return isMacLike ? Keys.chord(Keys.COMMAND, "a") : Keys.chord(Keys.CONTROL, "a");
    }

    /** Reliable clear using Select‑All + Delete (mimics real user behavior). */
    public void clearWithShortcut(WebElement element) {
        waitUntilClickable(element);
        scrollIntoViewCenter(element);
        element.sendKeys(selectAllChord());
        element.sendKeys(Keys.DELETE);
    }

    /** Clear with shortcut then type text (null‑safe). */
    public void clearAndType(WebElement element, String text) {
        clearWithShortcut(element);
        if (text != null) {
            element.sendKeys(text);
        }
    }

    /** Scroll into center, clear via shortcut, then type. */
    public void scrollClearType(WebElement element, String text) {
        scrollIntoViewCenter(element);
        clearAndType(element, text);
    }

    /** Blur the current active element (useful to trigger validations). */
    public void blurActive() {
        js.executeScript("if (document.activeElement) document.activeElement.blur();");
    }

    /* -------------------- JS HELPERS -------------------- */

    public void clickByJS(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /** Scroll element into center block for better visibility and fewer intercepts. */
    public void scrollIntoViewCenter(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    public void highlightElement(WebElement element) {
        js.executeScript("arguments[0].style.border='2px solid red'", element);
    }

    public String getTitleByJS() {
        return (String) js.executeScript("return document.title;");
    }

    public void refreshByJS() {
        js.executeScript("history.go(0)");
    }

    /** Public JS click wrapper with consistent naming. */
    public void jsClick(WebElement element) {
        clickByJS(element);
    }

    /* -------------------- VALUE/STALE/TEXT HELPERS -------------------- */

    /** Safe attribute getter (handles stale by returning empty). */
    public String safeGetAttribute(WebElement element, String name) {
        try {
            return element.getAttribute(name);
        } catch (StaleElementReferenceException e) {
            return "";
        }
    }

    /**
     * Wait for an input's "value" to change within the default timeout.
     * Returns the new value (no trimming).
     */
    public String waitValueToChange(WebElement element) {
        String before = safeGetAttribute(element, "value");
        getWait().until(d -> {
            String now = safeGetAttribute(element, "value");
            return now != null && !now.equals(before);
        });
        return safeGetAttribute(element, "value");
    }

    /**
     * Wait for an input's "value" to change with a custom timeout.
     * Returns the new value (no trimming).
     */
    public String waitValueToChange(WebElement element, Duration customTimeout) {
        String before = safeGetAttribute(element, "value");
        new WebDriverWait(driver, customTimeout).until(d -> {
            String now = safeGetAttribute(element, "value");
            return now != null && !now.equals(before);
        });
        return safeGetAttribute(element, "value");
    }

    /**
     * Get visible texts for a list with a simple stale retry per element.
     */
    public List<String> getTextsHandlingStale(List<WebElement> elements) {
        allVisible(elements);
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            try {
                texts.add(elements.get(i).getText().trim());
            } catch (StaleElementReferenceException e) {
                // wait for staleness, then re-read fresh reference
                getWait().until(ExpectedConditions.stalenessOf(elements.get(i)));
                texts.add(elements.get(i).getText().trim());
            }
        }
        return texts;
    }

    /* -------------------- MISC -------------------- */

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}