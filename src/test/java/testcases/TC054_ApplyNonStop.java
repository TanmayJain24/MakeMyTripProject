package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;
import utilities.ScreenshotUtility;

public class TC054_ApplyNonStop extends BaseTest {
    @Test
    public void NonStopFilter() {
        FlightBookingPage pg = new FlightBookingPage(driver,wait);
        pg.selectFromCity("Chennai");
        pg.selectToCity("Mumbai");
        pg.selectDate("22","April 2026");
        pg.travellerSelectAdult("1");
        pg.searchResults();
        boolean results = pg.applyNonStopFilter();
        ScreenshotUtility.takeScreenShot(driver, "NonStopFilterApplied");
        Assert.assertTrue(results, "Student Fare Not Selected");
    }
}
