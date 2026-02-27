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

    public WebDriverWait getWait() {
        return new WebDriverWait(driver, timeout);
    }

    public WebElement visible(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement visible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void clickWhenClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public WebElement waitUntilClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public List<WebElement> allVisible(List<WebElement> elements) {
        return getWait().until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void pageReady() {
        getWait().until(driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState")
        );
    }

    public void safeClick(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement el = getWait().until(ExpectedConditions.elementToBeClickable(locator));
                try {
                    el.click();
                    return;
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center', inline:'center'});", el);
                    el.click();
                    return;
                }
            } catch (StaleElementReferenceException sere) {
            } catch (Exception e) {
                try {
                    WebElement fresh = getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center', inline:'center'});", fresh);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fresh);
                    return;
                } catch (StaleElementReferenceException ignored) {
                }
            }
            attempts++;
        }
        throw new TimeoutException("safeClick failed after retries for locator: " + locator);
    }

    public void safeClickToWebElement(WebElement element) {
        WebDriverWait wait = getWait();
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(element));
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});", el);
                el.click();
                return;
            } catch (StaleElementReferenceException sere) {
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});", element);
            } catch (JavascriptException ignored) {
            }
            attempts++;
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception ex) {
            throw new TimeoutException("safeClick(WebElement) failed after retries.", ex);
        }
    }

    public void enterText(WebElement element, String text) {
        WebElement el = getWait().until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
        el.clear();
        el.sendKeys(text);
    }

    public CharSequence selectAllChord() {
        return isMacLike ? Keys.chord(Keys.COMMAND, "a") : Keys.chord(Keys.CONTROL, "a");
    }

    public void clearWithShortcut(WebElement element) {
        waitUntilClickable(element);
        scrollIntoViewCenter(element);
        element.sendKeys(selectAllChord());
        element.sendKeys(Keys.DELETE);
    }

    public void clearAndType(WebElement element, String text) {
        clearWithShortcut(element);
        if (text != null) {
            element.sendKeys(text);
        }
    }

    public void scrollClearType(WebElement element, String text) {
        scrollIntoViewCenter(element);
        clearAndType(element, text);
    }

    public void blurActive() {
        js.executeScript("if (document.activeElement) document.activeElement.blur();");
    }

    public void clickByJS(WebElement element) {
        js.executeScript("arguments[0].click();",element);
    }

    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

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

    public void jsClick(WebElement element) {
        clickByJS(element);
    }

    public String safeGetAttribute(WebElement element, String name) {
        try {
            return element.getAttribute(name);
        } catch (StaleElementReferenceException e) {
            return "";
        }
    }

    public String waitValueToChange(WebElement element) {
        String before = safeGetAttribute(element, "value");
        getWait().until(d -> {
            String now = safeGetAttribute(element, "value");
            return now != null && !now.equals(before);
        });
        return safeGetAttribute(element, "value");
    }

    public String waitValueToChange(WebElement element, Duration customTimeout) {
        String before = safeGetAttribute(element, "value");
        new WebDriverWait(driver, customTimeout).until(d -> {
            String now = safeGetAttribute(element, "value");
            return now != null && !now.equals(before);
        });
        return safeGetAttribute(element, "value");
    }

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

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}