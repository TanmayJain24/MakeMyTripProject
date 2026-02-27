package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;

public class TC013_HighestRating extends BaseTest {

    @Test
    public void testTopRatedDescendingFlow() {
        BusBookingPage busPage = new BusBookingPage(driver, wait);
        busPage.navigateToBuses();
        busPage.enterFromCity("Pune");
        busPage.enterToCity("Indore");
        busPage.selectTomorrow();
        busPage.clickSearch();
        busPage.applyTopRatedFilter();
        System.out.println("Applied Top Rated filter.");
        busPage.sortByRatingDescending();
        System.out.println("Sorted by Rating .");
        String busInfo = busPage.getTopRatedBusDetails();
        System.out.println("===============================================");
        System.out.println("FINAL RESULT:");
        System.out.println(busInfo);
        System.out.println("===============================================");
        Assert.assertNotNull(busInfo, "Bus info should not be null.");
        Assert.assertFalse(busInfo.contains("Could not retrieve"), "Failed to extract rating details.");
        Assert.assertTrue(busInfo.contains("Rating"), "Result should include the bus rating.");
    }
}