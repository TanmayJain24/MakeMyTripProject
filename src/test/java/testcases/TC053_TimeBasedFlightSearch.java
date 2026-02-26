package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;

public class TC053_TimeBasedFlightSearch extends BaseTest {
    @Test
    public void verifyTimeBasedFilters() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);

        // 1. Search Details
        fp.selectFromCity("Chennai");
        fp.selectToCity("Mumbai");
        fp.selectDate(driver, "10", "December 2026");
        fp.travellerSelectAdult(driver,"2");
        fp.searchResults(driver);

        // 2. Apply the Departure Filter (After 6 PM)
        boolean afterRes = fp.filterDepartureAfter6PM();

        // 3. Apply the Arrival Filter (Before 6 AM)
        boolean befoRes = fp.filterArrivalBefore6AM();

//        boolean results = true;
//        if(afterRes && befoRes) {
//            results true;
//        }
        // 4. Print all available flights after filtering
        // This will show only flights matching your timing criteria
        fp.printResults(driver, "Late Night - Early Morning Window");
        System.out.println("Test Case : Completed successfully.");
        Assert.assertTrue(afterRes, "Late Night - Early Morning filters Not Selected successfully");

    }
}