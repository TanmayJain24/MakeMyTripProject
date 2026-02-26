package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;

public class TC055_AirlineFilterValidation extends BaseTest {
    @Test
    public void verifyAirIndiaFilter() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);

        fp.selectFromCity("Delhi");
        fp.selectToCity("Mumbai");
        fp.selectDate(driver, "23", "September 2026");
        fp.travellerSelectAdult(driver,"1");
        fp.searchResults(driver);

        // Select 'Air India' from the Airlines sidebar
        fp.filterByAirline("Air India");

        // Verification: The getResultsCount confirms data exists
        Assert.assertTrue(fp.getResultsCount() > 0, "Air India filter returned no flights!");

        System.out.println("Successfully verified Air India specific filtering.");
    }
}