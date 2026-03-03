package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;
import utilities.Log;
import java.time.Duration;

public class TC011_FastestBusDetails extends BaseTest {
    @Test
    public void testIdentifyFastestBus() {
        Log.info("Starting TC012: Identify Fastest Bus Workflow");
        BusBookingPage busPage = new BusBookingPage(driver, Duration.ofSeconds(20));
        busPage.navigateToBuses();
        busPage.enterFromCity("Pune");
        busPage.enterToCity("Indore");
        busPage.selectTomorrow();
        busPage.clickSearch();
        busPage.sortByFastest();
        String fastestBusInfo = busPage.getFirstBusDetails();
        Log.info("===============================================");
        Log.info("FASTEST BUS DETAILS");
        Log.info(fastestBusInfo);
        Log.info("===============================================");
        Assert.assertNotNull(fastestBusInfo, "Bus details should not be null.");
        Assert.assertFalse(fastestBusInfo.contains("Could not extract"), "Failed to retrieve details from the UI. Check if bus cards loaded.");
        Assert.assertTrue(fastestBusInfo.contains("h") || fastestBusInfo.contains("m"), "Duration format is unexpected. Expected 'h' or 'm' in: " + fastestBusInfo);
        Log.info("TC012: Fastest bus identification completed successfully.");
    }
}