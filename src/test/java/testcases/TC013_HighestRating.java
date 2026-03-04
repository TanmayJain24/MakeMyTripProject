package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;
import utilities.Log;
import java.time.Duration;

public class TC013_HighestRating extends BaseTest {
    @Test
    public void testTopRatedDescendingFlow() {
        Log.info("Starting TC013: Highest Rated Bus Selection Workflow");
        BusBookingPage busPage = new BusBookingPage(driver, Duration.ofSeconds(20));
        busPage.navigateToBuses();
        busPage.enterFromCity("Pune");
        busPage.enterToCity("Indore");
        busPage.selectTomorrow();
        busPage.clickSearch();
        Log.info("Applying 'Top Rated' filter from the sidebar.");
        busPage.applyTopRatedFilter();
        Log.info("Sorting results by 'RATING' descending.");
        busPage.sortByRatingDescending();
        String busInfo = busPage.getTopRatedBusDetails();
        Log.info("===============================================");
        Log.info("FINAL RESULT: TOP RATED BUS");
        Log.info(busInfo);
        Log.info("===============================================");
        Assert.assertNotNull(busInfo, "Bus info should not be null.");
        Assert.assertFalse(busInfo.contains("Could not retrieve"), "Failed to extract rating details from the result list.");
        Assert.assertTrue(busInfo.contains("Rating"), "Result string does not contain 'Rating' label as expected: " + busInfo);
        Log.info("TC013: Highest rating test completed successfully.");
    }
}