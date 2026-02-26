package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;

public class TC054_ApplyNonStop extends BaseTest {
    @Test
    public void NonStopFilter()
    {
        FlightBookingPage pg = new FlightBookingPage(driver,wait);
        pg.selectFromCity("Chennai");
        pg.selectToCity("Mumbai");
        pg.selectDate(driver,"22","April 2026");
        pg.travellerSelectAdult(driver,"1");
        pg.searchResults(driver);
        boolean results = pg.applyNonStopFilter();


        // Assert that the results page confirms student benefit (usually via a badge or banner)
        Assert.assertTrue(results, "Student Fare Not Selected");
    }

}
