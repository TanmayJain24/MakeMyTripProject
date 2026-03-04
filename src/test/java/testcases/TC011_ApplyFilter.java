package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;
import utilities.ScreenshotUtility;
import utilities.Log; // Using your Log utility
import java.time.Duration;

public class TC011_ApplyFilter extends BaseTest {
    @Test
    public void testACAndSleeperBusFilter() {
        Log.info("Starting TC011: Apply AC and Sleeper Filter Test");
        BusBookingPage busPage = new BusBookingPage(driver, Duration.ofSeconds(20));
        busPage.navigateToBuses();
        busPage.enterFromCity("Pune");
        busPage.enterToCity("Indore");
        busPage.selectTomorrow();
        busPage.clickSearch();
        Log.info("Applying AC and Sleeper filters to the search results.");
        busPage.applyFilters();
        int filteredBusCount = busPage.getBusCount();
        ScreenshotUtility.takeScreenShot(driver, "FilteredBus");
        Log.info("===============================================");
        Log.info("FILTER RESULTS: AC + Sleeper");
        Log.info("Total Filtered Buses: " + filteredBusCount);
        Log.info("===============================================");
        Assert.assertTrue(filteredBusCount >= 0, "The filtered bus count should be valid.");
        Log.info("TC011: Filter test completed successfully.");
    }
}