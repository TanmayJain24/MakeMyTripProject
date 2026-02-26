package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class FlightBookingPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private int centerX;
    private int centerY;

    private final String flightsUrl = "https://www.goibibo.com/flights/";

    // From/To tiles
    private final By fromTile = By.xpath("//input[@id='fromCity']");
    private final By toTile = By.xpath("//input[@id='toCity']");
    private By fromCityInput = By.xpath("//input[@placeholder='From']");
    private By toCityInput = By.xpath("//input[@placeholder='To']");
    private By nextMonthLocator = By.xpath("//span[@aria-label='Next Month']");
    private By dateCaption = By.className("DayPicker-Caption");

    // Departure calendar
    private final By departureTile = By.xpath("//span[normalize-space()='Departure']");
    private final By regularBtn = By.xpath("//div[normalize-space()='Regular fares']");

    // Travellers & Class
    private final By travellers = By.xpath("//span[@class='lbl_input appendBottom5']");
    private final By applyAdultTravellers = By.xpath("//ul[@class='guestCounter font12 darkText gbCounter']/descendant::li[contains(@data-cy, 'adults')]");
    private final By applyChildTravellers = By.xpath("//div[@class='makeFlex column childCounter']/ul//li");
    private final By applyInfantsTravellers = By.xpath("//div[@class='makeFlex column pushRight infantCounter']/ul//li");

    private final By classesAvailable = By.xpath("//ul[@class='guestCounter classSelect font12 darkText']/Li");
    private final By applyBtn = By.xpath("//button[@class='primaryBtn btnApply pushRight']");
    private final By searchBtn = By.xpath("//a[contains(@class,'primaryBtn font24 latoBold widgetSearchBtn')]");

    // Results
    private final By searchResultLoc = By.xpath("//div[@data-test='component-clusterItem']");

    // Filter
    private final By airlineLoc = By.xpath("(//p[@class='checkboxTitle'][normalize-space()='Air India'])[1]");
    private final By gotItBtn = By.xpath("//span[@class='button buttonPrimary pushRight widthFitContent']");
    private final By Stud = By.xpath("//li[contains(@class,'active')]//span[@class='radioSelect']");
    private final By after6pm = By.xpath("//div[p[contains(text(),'Departure')]]//div[contains(@class,'filterTimeSlots')][div[normalize-space()='After 6 pm']]");
    private final By before6am = By.xpath("//div[p[contains(text(),'Arrival')]]//div[contains(@class,'filterTimeSlots')][div[normalize-space()='Before 6 am']]");
    private final By nonStopBtn = By.xpath("//p[normalize-space()='Non Stop First']");

    public FlightBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.js = (JavascriptExecutor) driver;

        // Initializing global center coordinates for selectFromCity
        this.centerX = driver.manage().window().getSize().getWidth() / 2;
        this.centerY = driver.manage().window().getSize().getHeight() / 2;
    }

    public boolean selectFromCity(String from) {
        js.executeScript("document.elementFromPoint(arguments[0], arguments[1]).click();", centerX, centerY);
        wait.until(ExpectedConditions.elementToBeClickable(fromTile)).click();
        wait.until(ExpectedConditions.elementToBeClickable(fromCityInput)).sendKeys(from);
        By fromOptionLocator = By.xpath("//li[@id='react-autowhatever-1-section-0-item-0']//div[@class='revampedSuggestionContent']");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        js.executeScript("arguments[0].click();", fromOption);
        System.out.println("Selected From city: " + from);
        return wait.until(ExpectedConditions.textToBePresentInElementValue(fromTile, from));
    }

    public boolean selectToCity(String to) {
        wait.until(ExpectedConditions.elementToBeClickable(toTile)).click();
        wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).sendKeys(to);
        By fromOptionLocator = By.xpath("//li[@id='react-autowhatever-1-section-0-item-0']//div[@class='revampedSearchSuggestionMain']");
        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
        js.executeScript("arguments[0].click();", fromOption);
        System.out.println("Selected To city: " + to);
        return wait.until(ExpectedConditions.textToBePresentInElementValue(toTile, to));
    }

    public boolean selectDate(WebDriver driver, String day, String mYear) {
        wait.until(ExpectedConditions.elementToBeClickable(departureTile)).click();
        boolean monthFound = false;
        while (!monthFound) {
            List<WebElement> visibleMonths = driver.findElements(dateCaption);
            for (WebElement month : visibleMonths) {
                if (month.getText().equalsIgnoreCase(mYear)) {
                    monthFound = true;
                    break;
                }
            }
            if (!monthFound) {
                WebElement nextArrow = wait.until(ExpectedConditions.elementToBeClickable(nextMonthLocator));
                nextArrow.click();
            }
        }
        String dateXpath = "//div[contains(.,'" + mYear + "')]/ancestor::div[@class='DayPicker-Month']//div[@class='DayPicker-Day']//p[text()='" + day + "']";
        WebElement dateElem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateXpath)));
        dateElem.click();
        wait.until(ExpectedConditions.elementToBeClickable(regularBtn)).click();
        return true;
    }

    public String travellerSelectAdult(WebDriver driver,String adult) {
        WebElement TravellerSelect = wait.until(ExpectedConditions.elementToBeClickable(travellers));
        TravellerSelect.click();
        List<WebElement> adultTravellers = driver.findElements(applyAdultTravellers);
        String clickedValue = "";
        for (WebElement getAdult : adultTravellers) {
            if (getAdult.getText().equals(adult)) {
                getAdult.click();
                clickedValue = getAdult.getText();
                break;
            }
        }
        return clickedValue;
    }

    public void travellerSelectChild(WebDriver driver,String child) {
        List<WebElement> childTravellers = driver.findElements(applyChildTravellers);
        for (WebElement getChild : childTravellers) {
            if (getChild.getText().equals(child)) {
                getChild.click();
                break;
            }
        }
    }

    public void travellerSelectInfant(WebDriver driver,String child) {
        List<WebElement> infantTravellers = driver.findElements(applyInfantsTravellers);
        for (WebElement getInfant : infantTravellers) {
            if (getInfant.getText().equals(child)) {
                getInfant.click();
                break;
            }
        }
    }

    public String selectClass(WebDriver driver, String classToGive) {
        List<WebElement> allClasses = driver.findElements(classesAvailable);
        String selected = "Not Found";
        for (WebElement cl : allClasses) {
            String availableClass = cl.getText().trim().replace("\n", " ");
            if (availableClass.toLowerCase().contains(classToGive.toLowerCase())) {
                cl.click();
                System.out.println("\n--- Selected Class: " + availableClass + " ---");
                selected = availableClass;
                break;
            }
        }
        return selected;
    }

    public void searchResults(WebDriver driver) {
        wait.until(ExpectedConditions.elementToBeClickable(applyBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();

        try {
            WebElement gotIt = wait.until(ExpectedConditions.presenceOfElementLocated(gotItBtn));
            js.executeScript("arguments[0].scrollIntoView(true);", gotIt);
            js.executeScript("arguments[0].click();", gotIt);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(gotItBtn));
            System.out.println("Auto-scroll intercepted and 'GOT IT' popup closed.");
        } catch (Exception e) {
            System.out.println("Popup did not appear or was handled by override.");
        }
        js.executeScript("document.elementFromPoint(arguments[0], arguments[1]).click();", centerX, centerY);

    }

    public void filterByAirline(String airlineName) {
        WebElement airlineFilter = wait.until(ExpectedConditions.presenceOfElementLocated(airlineLoc));
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", airlineFilter);
        js.executeScript("arguments[0].click();", airlineFilter);
        System.out.println("Applied filter for: " + airlineName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultLoc));
    }

    public int getResultsCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(searchResultLoc));
        return driver.findElements(searchResultLoc).size();
    }

    public void printResults(WebDriver driver,String selectedClass) {
        wait.until(ExpectedConditions.presenceOfElementLocated(searchResultLoc));
        System.out.println("\n===========================================================================");
        System.out.println("CLEANED FLIGHT RESULTS FOR: " + selectedClass.toUpperCase());
        System.out.println("===========================================================================");
        System.out.println(String.format("%-15s | %-12s | %-12s | %-10s | %-10s", "AIRLINE", "FROM", "TO", "TIME", "PRICE"));
        System.out.println("---------------------------------------------------------------------------");

        Set<String> processedFlights = new HashSet<>();
        int totalFlightsFound = 0;
        int lastCount = -1;

        while (totalFlightsFound > lastCount) {
            lastCount = totalFlightsFound;
            List<WebElement> searchResultList = driver.findElements(searchResultLoc);
            for (WebElement row : searchResultList) {
                try {
                    List<WebElement> nameElements = row.findElements(By.className("airlineName"));
                    String airline = !nameElements.isEmpty() ? nameElements.get(0).getText() : "Unknown";
                    List<WebElement> timeElements = row.findElements(By.className("flightTimeInfo"));
                    String time = !timeElements.isEmpty() ? timeElements.get(0).getText() : "N/A";
                    String rowID = airline + "_" + time;

                    if (!processedFlights.contains(rowID) && !airline.equals("Unknown")) {
                        List<WebElement> priceElements = row.findElements(By.xpath(".//div[contains(@class, 'clusterViewPrice')]/span[not(contains(@class, 'slashedPrice'))]"));
                        String price = "N/A";
                        if (!priceElements.isEmpty()) {
                            price = "Rs. " + priceElements.get(0).getText().replace("₹", "").trim();
                        }
                        System.out.println(String.format("%-15s | %-12s | %-12s | %-10s | %-10s", airline, "Origin", "Destination", time, price));
                        processedFlights.add(rowID);
                        totalFlightsFound++;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            js.executeScript("window.scrollBy(0, 1000);");
            try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
            if (totalFlightsFound > 30) break;
        }
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Total Unique Flights Found: " + totalFlightsFound);
    }

    public void selectFareType(String type) {
        By fareLoc = By.xpath("//div[normalize-space()='"+type+"']");
        wait.until(ExpectedConditions.elementToBeClickable(fareLoc)).click();
        System.out.println("Selected Fare Type: " + type);
    }

    public boolean applyNonStopFilter() {
        WebElement nonStopElement = wait.until(ExpectedConditions.presenceOfElementLocated(nonStopBtn));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", nonStopElement);
        js.executeScript("arguments[0].click();", nonStopElement);
        return nonStopElement.isEnabled();

    }

    public boolean filterDepartureAfter6PM() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(after6pm));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        js.executeScript("arguments[0].click();", element);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResultLoc));
        System.out.println("Applied: Departure After 6 PM");
        return element.isEnabled();
    }

    public boolean filterArrivalBefore6AM() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(before6am));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        js.executeScript("arguments[0].click();", element);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResultLoc));
        System.out.println("Applied: Arrival Before 6 AM");
        return element.isEnabled();
    }

    public boolean confirmSelection() {
        WebElement StudRadio = wait.until(ExpectedConditions.visibilityOfElementLocated(Stud));
        return StudRadio.isDisplayed();
    }
}
