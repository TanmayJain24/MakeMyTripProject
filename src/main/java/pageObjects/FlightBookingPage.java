package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FlightBookingPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

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

    public FlightBookingPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean selectFromCity(String from) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int x = driver.manage().window().getSize().getWidth() / 2;
        int y = driver.manage().window().getSize().getHeight() / 2;
        js.executeScript("document.elementFromPoint(arguments[0], arguments[1]).click();", x, y);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
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

    public String travellerSelectAdult(WebDriver driver, String adult) {
        WebElement TravellerSelect = wait.until(ExpectedConditions.elementToBeClickable(travellers));
        TravellerSelect.click();
        List<WebElement> adultTravellers = driver.findElements((applyAdultTravellers));
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

    public void travellerSelectChild(WebDriver driver, String child) {
        List<WebElement> childTravellers = driver.findElements((applyChildTravellers));
        for (WebElement getChild : childTravellers) {
            if (getChild.getText().equals(child)) {
                getChild.click();
                break;
            }
        }
    }

    public void travellerSelectInfant(WebDriver driver, String child) {
        List<WebElement> infantTravellers = driver.findElements((applyInfantsTravellers));
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
        By popupCancelBtn = By.xpath("//span[@class='bgProperties overlayCrossIcon icon20']");
        List<WebElement> popupElements = driver.findElements(popupCancelBtn);
        if (popupElements.size() > 0) {
            System.out.println("Ad popup detected. Closing now...");
            wait.until(ExpectedConditions.elementToBeClickable(popupCancelBtn)).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupCancelBtn));
        }
    }

    public int getResultsCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(searchResultLoc));
        return driver.findElements(searchResultLoc).size();
    }

    public void printResults(WebDriver driver, String selectedClass) {
        wait.until(ExpectedConditions.presenceOfElementLocated(searchResultLoc));
        System.out.println("\n===========================================================================");
        System.out.println("CLEANED FLIGHT RESULTS FOR: " + selectedClass.toUpperCase());
        System.out.println("===========================================================================");
        System.out.println(String.format("%-15s | %-12s | %-12s | %-10s | %-10s", "AIRLINE", "FROM", "TO", "TIME", "PRICE"));
        System.out.println("---------------------------------------------------------------------------");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        java.util.Set<String> processedFlights = new java.util.HashSet<>();
        int totalFlightsFound = 0;
        int lastCount = -1;

        while (totalFlightsFound > lastCount) {
            lastCount = totalFlightsFound;
            List<WebElement> searchResultList = driver.findElements(searchResultLoc);
            for (WebElement row : searchResultList) {
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
                    System.out.println(String.format("%-15s | %-12s | %-12s | %-10s | %-10s", airline, "Chennai", "Pune", time, price));
                    processedFlights.add(rowID);
                    totalFlightsFound++;
                }
            }
            js.executeScript("window.scrollBy(0, 1000);");
            try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
            if (totalFlightsFound > 30) break;
        }
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Total Unique Flights Found: " + totalFlightsFound);
    }
}
























//package pageObjects;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.List;
//import java.util.Locale;
//
//public class FlightBookingPage {
//    private final WebDriver driver;
//    private final WebDriverWait wait;
//
//    private final String flightsUrl = "https://www.goibibo.com/flights/";
//
//    // From/To tiles (focus these first)
//    private final By fromTile = By.xpath("//input[@id='fromCity']");
//    private final By toTile = By.xpath("//input[@id='toCity']");
//    //li[@id='react-autowhatever-1-section-0-item-0']//div[@class='revampedSuggestionContent']
//    private By fromCityInput = By.xpath("//input[@placeholder='From']");
//    private By toCityInput = By.xpath("//input[@placeholder='To']");
//    private By nextMonthLocator = By.xpath("//span[@aria-label='Next Month']");
//    private By dateCaption = By.className("DayPicker-Caption");
//
//
//    // Departure calendar
//    private final By departureTile = By.xpath("//span[normalize-space()='Departure']");
//    private final By calendarGrid = By.xpath("//div[@class='DayPicker-Months']");
////  private final By eleMonth = By.xpath("//body/div[@id='root']/div[@id='top-banner']/div[@class='minContainer']/div/div[@class='flightWidgetSection appendBottom40']/div[@class='flightSearchWidget']/div[contains(@class,'searchWidgetContainer')]/div[contains(@class,'fltWidgetSection appendBottom40 primaryTraveler')]/div[@class='fsw widgetOpen']/div[@class='fsw_inner returnPersuasion']/div[@class='flt_fsw_inputBox dates inactiveWidget activeWidget']/div[@class='datePickerContainer']/div[@class='flightCalOverlay']/div[@class='dayPickerFlightWrap']/div[@class='RangeExample']/div[@class='DayPicker Selectable Range']/div[@class='DayPicker-wrapper']/div[@class='DayPicker-Months']/div[2]/div[1]/div[1]");
////    private final By monthYear = By.xpath("//div[contains(text(),'March 2026')]");
//    private final By dates = By.xpath("//div[@class='DayPicker-Body']//div");
//
//    //
//    private final By regularBtn = By.xpath("//div[normalize-space()='Regular fares']");
//
//
//    // Travellers & Class
//    private final By travellers = By.xpath("//span[@class='lbl_input appendBottom5']");
//    private final By applyAdultTravellers = By.xpath("//ul[@class='guestCounter font12 darkText gbCounter']/descendant::li[contains(@data-cy, 'adults')]");
//    private final By applyChildTravellers = By.xpath("//div[@class='makeFlex column childCounter']/ul//li");
//    private final By applyInfantsTravellers = By.xpath("//div[@class='makeFlex column pushRight infantCounter']/ul//li");
//
//    private final By classesAvailable = By.xpath("//ul[@class='guestCounter classSelect font12 darkText']/Li");
//    private final By applyBtn = By.xpath("//button[@class='primaryBtn btnApply pushRight']");
//    private final By searchBtn = By.xpath("//a[contains(@class,'primaryBtn font24 latoBold widgetSearchBtn')]");
//
//    //
//    private final By searchResultLoc = By.xpath("//div[@data-test='component-clusterItem']");
////    private final By searchResultLoc = By.xpath("//div[@class='listingCard  appendBottom5']");
//
//    public FlightBookingPage(WebDriver driver, WebDriverWait wait)
//    {
//        this.driver = driver;
//        this.wait = wait; // use the shared wait from BaseTest as-is
//    }
//
//    // ---------- overlays ----------
//
//
//    public boolean selectFromCity(String from)
//    {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        int x = driver.manage().window().getSize().getWidth() / 2;
//        int y = driver.manage().window().getSize().getHeight() / 2;
//        js.executeScript("document.elementFromPoint(arguments[0], arguments[1]).click();", x, y);
//        wait.until(ExpectedConditions.elementToBeClickable(fromTile)).click();
//        wait.until(ExpectedConditions.elementToBeClickable(fromCityInput)).sendKeys(from);
////      By fromOptionLocator = By.xpath("//p[contains(text(),'" + from + "')]");
//        By fromOptionLocator = By.xpath("//li[@id='react-autowhatever-1-section-0-item-0']//div[@class='revampedSuggestionContent']");
//        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
//        js.executeScript("arguments[0].click();", fromOption);
//        System.out.println("Selected From city: " + from);
//        wait.until(ExpectedConditions.textToBePresentInElementValue(fromTile, from));
//        String fromCityText = wait.until(ExpectedConditions.elementToBeClickable(fromTile)).getAttribute("value");
//        return fromCityText.contains(from);
//    }
//
//    // Select To city with dropdown
//    public boolean selectToCity(String to) {
//        wait.until(ExpectedConditions.elementToBeClickable(toTile)).click();
//        wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).sendKeys(to);
//        //By fromOptionLocator = By.xpath("//p[contains(text(),'" + to + "')]");
//        By fromOptionLocator = By.xpath("//li[@id='react-autowhatever-1-section-0-item-0']//div[@class='revampedSearchSuggestionMain']");
//        WebElement fromOption = wait.until(ExpectedConditions.visibilityOfElementLocated(fromOptionLocator));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", fromOption);
//        System.out.println("Selected To city: " + to);
//        wait.until(ExpectedConditions.textToBePresentInElementValue(toTile, to));
//        String toCityText = wait.until(ExpectedConditions.elementToBeClickable(toTile)).getAttribute("value");
//        return toCityText.contains(to);
//    }
//
//
//    public void selectDate(WebDriver driver, String day, String mYear)
//    {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        // 1. Open the calendar
//        wait.until(ExpectedConditions.elementToBeClickable(departureTile)).click();
//
//        // 2. Loop to find the correct Month/Year
//        boolean monthFound = false;
//        while (!monthFound) {
//            List<WebElement> visibleMonths = driver.findElements(dateCaption);
//            for (WebElement month : visibleMonths) {
//                if (month.getText().equalsIgnoreCase(mYear)) {
//                    monthFound = true;
//                    break;
//                }
//            }
//            if (!monthFound) {
//                // Click Next Month arrow - Re-finding every time to avoid StaleElement
//                WebElement nextArrow = wait.until(ExpectedConditions.elementToBeClickable(nextMonthLocator));
//                nextArrow.click();
//            }
//        }
//
//        // 3. Find the date within the correct month
//        // We use an XPath that targets the specific month container and then the day number
//        String dateXpath = "//div[contains(.,'" + mYear + "')]/ancestor::div[@class='DayPicker-Month']" +
//                "//div[@class='DayPicker-Day']//p[text()='" + day + "']";
//
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateXpath))).click();
//        System.out.println("Date " + day + " for " + mYear + " selected successfully!");
//        wait.until(ExpectedConditions.elementToBeClickable(regularBtn)).click();
//    }
//
//
//    public void travellerSelectAdult(WebDriver driver, String adult)
//    {
//          //By applyAdultTravellers = By.xpath("//li[@data-cy='adults-" + adult+ "']");
//
//          WebElement TravellerSelect = wait.until(ExpectedConditions.elementToBeClickable(travellers));
//          TravellerSelect.click();
//          List<WebElement> adultTravellers = driver.findElements((applyAdultTravellers));
//          for(WebElement getAdult : adultTravellers)
//          {
//            if(getAdult.getText().equals(adult))
//            {
//                getAdult.click();
//                break;
//            }
//        }
//    }
//
//    public void travellerSelectChild(WebDriver driver, String child)
//    {
//        List<WebElement> childTravellers = driver.findElements((applyChildTravellers));
//        for(WebElement getChild : childTravellers)
//        {
//            if(getChild.getText().equals(child))
//            {
//                getChild.click();
//                break;
//            }
//        }
//    }
//    public void travellerSelectInfant(WebDriver driver, String child)
//    {
//        List<WebElement> infantTravellers = driver.findElements((applyInfantsTravellers));
//        for(WebElement getInfant : infantTravellers)
//        {
//            if(getInfant.getText().equals(child))
//            {
//                getInfant.click();
//                break;
//            }
//        }
//    }
//
//    public void selectClass(WebDriver driver, String classToGive) {
//        // 1. Select the Class
//        List<WebElement> allClasses = driver.findElements(classesAvailable);
//        boolean classFound = false;
//
//        for (WebElement cl : allClasses) {
//            // Clean the text to avoid hidden newlines
//            String availableClass = cl.getText().trim().replace("\n", " ");
//
//            if (availableClass.toLowerCase().contains(classToGive.toLowerCase())) {
//                cl.click();
//                // This is the line that was likely missing or not triggering
//                System.out.println("\n--- Selected Class: " + availableClass + " ---");
//                classFound = true;
//                break;
//            }
//        }
//
//        if(!classFound) {
//            System.out.println("\n--- Selected Class: Default (Economy) ---");
//        }
//    }
//
//
//
//    public void searchResults(WebDriver driver) {
//
//        wait.until(ExpectedConditions.elementToBeClickable(applyBtn)).click();
//        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
//        // --- POPUP HANDLER (Dynamic Check) ---
//        By popupCancelBtn = By.xpath("//span[@class='bgProperties overlayCrossIcon icon20']");
//        List<WebElement> popupElements = driver.findElements(popupCancelBtn);
//
//        if (popupElements.size() > 0) {
//            System.out.println("Ad popup detected. Closing now...");
//            wait.until(ExpectedConditions.elementToBeClickable(popupCancelBtn)).click();
//
//            // Brief wait to allow the overlay to disappear from the DOM
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupCancelBtn));
//        }
//    }
//public void printResults(WebDriver driver, String selectedClass) {
//    wait.until(ExpectedConditions.presenceOfElementLocated(searchResultLoc));
//
//    System.out.println("\n===========================================================================");
//    System.out.println("CLEANED FLIGHT RESULTS FOR: " + selectedClass.toUpperCase());
//    System.out.println("===========================================================================");
//    System.out.println(String.format("%-15s | %-12s | %-12s | %-10s | %-10s", "AIRLINE", "FROM", "TO", "TIME", "PRICE"));
//    System.out.println("---------------------------------------------------------------------------");
//
//    // ... rest of your scrolling logic ...
//
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        java.util.Set<String> processedFlights = new java.util.HashSet<>(); // To avoid duplicates
//        int totalFlightsFound = 0;
//        int lastCount = -1;
//
//        // Scroll and collect until no more new flights are loaded
//        while (totalFlightsFound > lastCount) {
//            lastCount = totalFlightsFound;
//            List<WebElement> searchResultList = driver.findElements(searchResultLoc);
//
//            for (WebElement row : searchResultList) {
//                // Use a unique key (Airline + Time) to ensure we don't print the same flight twice while scrolling
//                String rowID = "";
//
//                // A. Get Airline Name
//                List<WebElement> nameElements = row.findElements(By.className("airlineName"));
//                String airline = !nameElements.isEmpty() ? nameElements.get(0).getText() : "Unknown";
//
//                // B. Get Departure Time
//                List<WebElement> timeElements = row.findElements(By.className("flightTimeInfo"));
//                String time = !timeElements.isEmpty() ? timeElements.get(0).getText() : "N/A";
//
//                rowID = airline + "_" + time;
//
//                if (!processedFlights.contains(rowID) && !airline.equals("Unknown")) {
//                    // C. Get Actual Price
//                    List<WebElement> priceElements = row.findElements(By.xpath(".//div[contains(@class, 'clusterViewPrice')]/span[not(contains(@class, 'slashedPrice'))]"));
//                    String price = "N/A";
//                    if (!priceElements.isEmpty()) {
//                        price = "Rs. " + priceElements.get(0).getText().replace("₹", "").trim();
//                    }
//
//                    System.out.println(String.format("%-15s | %-12s | %-12s | %-10s | %-10s",
//                            airline, "Chennai", "Pune", time, price));
//
//                    processedFlights.add(rowID);
//                    totalFlightsFound++;
//                }
//            }
//
//            // Scroll down to load more flights
//            js.executeScript("window.scrollBy(0, 1000);");
//
//            // Short wait for the Virtual List to render new rows
//            try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
//
//            // Optional: Break if you have enough results (e.g., 20 flights) to save time
//            if (totalFlightsFound > 30) break;
//        }
//
//        System.out.println("---------------------------------------------------------------------------");
//        System.out.println("Total Unique Flights Found: " + totalFlightsFound);
//    }
//}
//
//
//
//
