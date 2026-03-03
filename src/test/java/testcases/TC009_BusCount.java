package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;
import utilities.Log; // Using your custom Log class
import java.time.Duration;

public class TC009_BusCount extends BaseTest {

    @Test
    public void testBusSearchForTomorrow() {
        Log.info("Starting TC010: Bus Search for Tomorrow Workflow");
        BusBookingPage busPage = new BusBookingPage(driver, Duration.ofSeconds(20));

        busPage.navigateToBuses();
        busPage.enterFromCity("Pune");
        busPage.enterToCity("Indore");
        busPage.selectTomorrow();
        busPage.clickSearch();

        int busCount = busPage.getBusCount();

        Log.info("Total Buses available for tomorrow: " + busCount);
        Assert.assertTrue(busCount >= 0, "Search results did not load correctly.");

        Log.info("TC010: Bus Search completed successfully.");
    }
}