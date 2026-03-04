package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;
import utilities.Log;

public class TC023_AirlineFilterValidation extends BaseTest {
    @Test
    public void verifyAirIndiaFilter() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);
        fp.selectFromCity("Delhi");
        fp.selectToCity("Mumbai");
        fp.selectDate("23", "September 2026");
        fp.travellerSelectAdult("6");
        fp.searchResults();
        fp.filterByAirline("Air India");
        Assert.assertTrue(fp.getResultsCount() > 0, "Air India filter returned no flights!");
        Log.info("Successfully verified Air India specific filtering..!");
    }
}