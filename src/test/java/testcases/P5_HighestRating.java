package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;

public class P5_HighestRating extends BaseTest {

    @Test
    public void testTopRatedDescendingFlow() throws InterruptedException {
        BusBookingPage busPage = new BusBookingPage(driver, wait);
        busPage.applyTopRatedFilter();
        System.out.println("Applied Top Rated filter.");
        busPage.sortByRatingDescending();
        System.out.println("Sorted by Rating (Descending).");
        String busInfo = busPage.getTopRatedBusDetails();
        System.out.println("===============================================");
        System.out.println("FINAL RESULT:");
        System.out.println(busInfo);
        System.out.println("===============================================");
        Assert.assertNotNull(busInfo, "Bus info should not be null.");
        Assert.assertTrue(busInfo.contains("Rating"), "Result should include the bus rating.");
    }
}