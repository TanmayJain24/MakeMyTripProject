package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonCode;
import utilities.Log;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class FlightBookingPage {
    WebDriver driver;
    CommonCode common;
    int centerX;
    int centerY;

    @FindBy(id = "fromCity")
    WebElement fromTileCard;

    @FindBy(id = "toCity")
    WebElement toTileCard;

    @FindBy(xpath = "//input[@placeholder='From']")
    WebElement fromCityInputTextBox;

    @FindBy(xpath = "//input[@placeholder='To']")
    WebElement toCityInputTextBox;

    @FindBy(xpath = "//span[@aria-label='Next Month']")
    WebElement nextMonthBtn;

    @FindBy(className = "DayPicker-Caption")
    List<WebElement> dateCaption;

    @FindBy(xpath = "//span[normalize-space()='Departure']")
    WebElement departureTile;

    @FindBy(xpath = "//div[normalize-space()='Regular fares']")
    WebElement regularBtn;

    @FindBy(xpath = "//span[contains(text(),'Travellers & Class')]")
    WebElement travellersOptions;

    @FindBy(xpath = "//ul[contains(@class, 'guestCounter font')]/descendant::li[contains(@data-cy, 'adults')]")
    List<WebElement> applyAdultTravellers;

    @FindBy(xpath = "//div[@class='makeFlex column childCounter']/ul//li")
    List<WebElement> applyChildTravellers;

    @FindBy(xpath = "//div[@class='makeFlex column pushRight infantCounter']/ul//li")
    List<WebElement> applyInfantsTravellers;

    @FindBy(xpath = "//ul[contains(@class, 'guestCounter classSelect')]/Li")
    List<WebElement> classesAvailable;

    @FindBy(xpath = "//button[@class='primaryBtn btnApply pushRight']")
    WebElement applyBtn;

    @FindBy(xpath = "//a[contains(@class,'widgetSearchBtn')]")
    WebElement searchBtn;

    @FindBy(xpath = "//div[@data-test='component-clusterItem']")
    List<WebElement> searchResults;

    @FindBy(xpath = "(//p[@class='checkboxTitle'][normalize-space()='Air India'])[1]")
    WebElement airlineFilter;

    @FindBy(xpath = "//span[@class='button buttonPrimary pushRight widthFitContent']")
    WebElement gotItBtn;

    @FindBy(xpath = "//div[p[contains(text(),'Departure')]]//div[contains(@class,'filterTimeSlots')][div[normalize-space()='After 6 pm']]")
    WebElement after6pm;

    @FindBy(xpath = "//div[p[contains(text(),'Arrival')]]//div[contains(@class,'filterTimeSlots')][div[normalize-space()='Before 6 am']]")
    WebElement before6am;

    @FindBy(xpath = "//p[normalize-space()='Non Stop First']")
    WebElement nonStopBtn;

    public FlightBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.common = new CommonCode(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
        this.centerX = driver.manage().window().getSize().getWidth() / 2;
        this.centerY = driver.manage().window().getSize().getHeight() / 2;
    }

    public boolean selectFromCity(String from) {
        common.clickAtCoordinates(centerX, centerY);
        common.safeClickToWebElement(fromTileCard);
        common.enterText(fromCityInputTextBox, from);
        By fromOptionLocator = By.xpath("//span[@class='revampedCityName' and contains(text(), '" + from + "')]");
        common.clickByJS(common.visible(fromOptionLocator));
        Log.info("Selected From city: " + from);
        return common.visible(fromTileCard).getAttribute("value").equalsIgnoreCase(from);
    }

    public boolean selectToCity(String to) {
        common.safeClickToWebElement(toTileCard);
        common.enterText(toCityInputTextBox, to);
        By toOptionLocator = By.xpath("//span[@class='revampedCityName' and contains(text(), '" + to + "')]");        common.clickByJS(common.visible(toOptionLocator));
        Log.info("Selected To city: " + to);
        return common.visible(toTileCard).getAttribute("value").equalsIgnoreCase(to);
    }

    public boolean selectDate(String day, String mYear) {
        common.safeClickToWebElement(departureTile);
        boolean monthFound = false;
        while (!monthFound) {
            for (WebElement month : dateCaption) {
                if (month.getText().equalsIgnoreCase(mYear)) {
                    monthFound = true;
                    break;
                }
            }
            if (!monthFound) {
                common.clickWhenClickable(nextMonthBtn);
            }
        }
        String dateXpath = "//div[contains(.,'" + mYear + "')]/ancestor::div[@class='DayPicker-Month']//div[@class='DayPicker-Day']//p[text()='" + day + "']";
        common.safeClick(By.xpath(dateXpath));
        common.safeClickToWebElement(regularBtn);
        return monthFound;
    }

    public String travellerSelectAdult(String adult) {
        common.safeClickToWebElement(travellersOptions);
        for (WebElement getAdult : applyAdultTravellers) {
            if (getAdult.getText().equals(adult)) {
                getAdult.click();
                return getAdult.getText();
            }
        }
        return "";
    }

    public void travellerSelectChild(String child) {
        for (WebElement getChild : applyChildTravellers) {
            if (getChild.getText().equals(child)) {
                getChild.click();
                break;
            }
        }
    }

    public void travellerSelectInfant(String infant) {
        for (WebElement getInfant : applyInfantsTravellers) {
            if (getInfant.getText().equals(infant)) {
                getInfant.click();
                break;
            }
        }
    }

    public String selectClass(String classToGive) {
        for (WebElement cl : classesAvailable) {
            String availableClass = cl.getText().trim().replace("\n", " ");
            if (availableClass.toLowerCase().contains(classToGive.toLowerCase())) {
                cl.click();
                Log.info("Selected Class: " + availableClass);
                return availableClass;
            }
        }
        return "Not Found";
    }

    public void searchResults() {
        common.safeClickToWebElement(applyBtn);
        common.safeClickToWebElement(searchBtn);
        common.pageReady();
        try {
            WebElement popup = common.waitUntilClickable(gotItBtn);
            common.clickByJS(popup);
            common.getWait().until(ExpectedConditions.invisibilityOf(popup));
        } catch (Exception e) {
            Log.info("No interstitial popup appeared.");
        }
        common.clickAtCoordinates(centerX, centerY);
    }

    public void filterByAirline(String airlineName) {
        common.scrollIntoView(airlineFilter);
        common.clickByJS(airlineFilter);
        common.allVisible(searchResults);
        Log.info("Applied filter for: " + airlineName);
    }

    public int getResultsCount() {
        return common.allVisible(searchResults).size();
    }

    public List<String[]> printAndCollectResults(String selectedClass, String from, String to) {
        common.allVisible(searchResults);
        Log.info("\n===========================================================================");
        Log.info("CLEANED FLIGHT RESULTS FOR: " + selectedClass.toUpperCase());
        Log.info("===========================================================================");
        Log.info(String.format("%-18s | %-12s | %-12s | %-10s | %-10s", "AIRLINE", "FROM", "TO", "TIME", "PRICE"));
        Log.info("---------------------------------------------------------------------------");

        List<String[]> flightDataList = new ArrayList<>();
        Set<String> processedFlights = new HashSet<>();
        int totalFlightsFound = 0;
        int lastCount = -1;

        while (totalFlightsFound > lastCount) {
            lastCount = totalFlightsFound;
            for (WebElement row : searchResults) {
                try {
                    String airline = row.findElement(By.className("airlineName")).getText();
                    String time = row.findElement(By.className("flightTimeInfo")).getText();
                    String rowID = airline + "_" + time;
                    if (!processedFlights.contains(rowID) && !airline.isEmpty()) {
                        List<WebElement> priceElements = row.findElements(By.xpath(".//div[contains(@class, 'clusterViewPrice')]/span[not(contains(@class, 'slashedPrice'))]"));
                        String price = !priceElements.isEmpty() ? "Rs. " + priceElements.get(0).getText().replace("₹", "").trim() : "N/A";
                        Log.info(String.format("%-18s | %-12s | %-12s | %-10s | %-10s", airline, from, to, time, price));
                        flightDataList.add(new String[]{airline, from, to, time, price});
                        processedFlights.add(rowID);
                        totalFlightsFound++;
                    }
                } catch (Exception ignored) {}
            }
            common.scrollByPixels(1000);
            common.waitFor(3000);
            if (totalFlightsFound > 30) break;
        }
        Log.info("---------------------------------------------------------------------------");
        Log.info("Total Unique Flights Found: " + totalFlightsFound);
        return flightDataList;
    }

    public void selectFareType(String type) {
        common.safeClick(By.xpath("//div[normalize-space()='" + type + "']"));
    }

    public boolean applyNonStopFilter() {
        common.scrollIntoViewCenter(nonStopBtn);
        common.clickByJS(nonStopBtn);
        return nonStopBtn.isDisplayed();
    }

    public boolean filterDepartureAfter6PM() {
        common.scrollIntoViewCenter(after6pm);
        common.clickByJS(after6pm);
        return !common.allVisible(searchResults).isEmpty();
    }

    public boolean filterArrivalBefore6AM() {
        common.scrollIntoViewCenter(before6am);
        common.clickByJS(before6am);
        return !common.allVisible(searchResults).isEmpty();
    }

    public boolean confirmSelection() {
        boolean status = common.visible(By.xpath("//li[contains(@class,'active')]//span[@class='radioSelect']")).isDisplayed();
        Log.info("Student fare type selected successfully..!");
        return status;
    }
}