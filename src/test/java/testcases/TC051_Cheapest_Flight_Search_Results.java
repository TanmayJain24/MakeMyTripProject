package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;

public class TC051_Cheapest_Flight_Search_Results extends BaseTest {
    @Test
    public void search_flights_results() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);

        // 1. Assert From City Selection
        boolean fromStatus = fp.selectFromCity("Chennai");
        Assert.assertTrue(fromStatus, "Departure city 'Chennai' selection failed!");

        // 2. Assert To City Selection
        boolean toStatus = fp.selectToCity("Mumbai");
        Assert.assertTrue(toStatus, "Destination city 'Mumbai' selection failed!");

        // 3. Assert Date Selection
        boolean dateStatus = fp.selectDate(driver, "12", "October 2026");
        Assert.assertTrue(dateStatus, "Date selection failed!");

        // 4. Assert Traveller Selection (Adults)
        String adultCount = fp.travellerSelectAdult(driver, "3");
        Assert.assertEquals(adultCount, "3", "Adult traveller count mismatch!");

        fp.travellerSelectChild(driver, "1");
        fp.travellerSelectInfant(driver, "0");

        // 5. Assert Class Selection
        String selectedClass = fp.selectClass(driver, "First Class");
        Assert.assertTrue(selectedClass.toLowerCase().contains("first class"),
                "Expected 'First Class' but found: " + selectedClass);

        // 6. Execute Search
        fp.searchResults(driver);

        // 7. Assert Search Results are loaded
        int totalResults = fp.getResultsCount();
        Assert.assertTrue(totalResults > 0, "Flight search returned 0 results!");

        // 8. Print and Scrape
        fp.printResults(driver, "First Class");
    }
}

