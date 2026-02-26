package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;

public class TC012_FastestBusDetails extends BaseTest {

    @Test
    public void testIdentifyFastestBus() {
        BusBookingPage busPage = new BusBookingPage(driver, wait);
        busPage.navigateToBuses();
        busPage.enterFromCity("Pune");
        busPage.enterToCity("Indore");
        busPage.selectTomorrow();
        busPage.clickSearch();
        System.out.println("Sorting by fastest duration...");
        busPage.sortByFastest();
        String fastestBusInfo = busPage.getFirstBusDetails();
        System.out.println("===============================================");
        System.out.println("FASTEST BUS DETAILS");
        System.out.println(fastestBusInfo);
        System.out.println("===============================================");
        Assert.assertNotNull(fastestBusInfo, "Bus details should not be null.");
        Assert.assertFalse(fastestBusInfo.contains("Could not extract"), "Failed to retrieve details from the UI.");
        Assert.assertTrue(fastestBusInfo.contains("h") || fastestBusInfo.contains("m"),
                "Duration format is unexpected: " + fastestBusInfo);
    }
}