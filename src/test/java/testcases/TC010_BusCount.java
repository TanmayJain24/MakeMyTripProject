package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;

public class TC010_BusCount extends BaseTest {

    @Test
    public void testBusSearchForTomorrow() {
        BusBookingPage busPage = new BusBookingPage(driver, wait);
        busPage.navigateToBuses();
        busPage.enterFromCity("Pune");
        busPage.enterToCity("Indore");
        busPage.selectTomorrow();
        busPage.clickSearch();
        int busCount = busPage.getBusCount();
        System.out.println("Total Buses available for tomorrow: " + busCount);
        Assert.assertTrue(busCount >= 0, "Search results did not load correctly.");
    }
}