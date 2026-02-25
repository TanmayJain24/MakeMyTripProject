package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;

public class P4_FastestBusDetails extends BaseTest {

    @Test
    public void testIdentifyFastestBus() throws InterruptedException {
        BusBookingPage busPage = new BusBookingPage(driver, wait);
        busPage.sortByFastest();
        String fastestBusInfo = busPage.getFirstBusDetails();
        System.out.println("===============================================");
        System.out.println("FASTEST BUS DETAILS");
        System.out.println(fastestBusInfo);
        System.out.println("===============================================");
        Assert.assertNotNull(fastestBusInfo, "Bus details should not be empty.");
        Assert.assertTrue(fastestBusInfo.contains("h"), "Duration should contain hours 'h'.");
    }
}