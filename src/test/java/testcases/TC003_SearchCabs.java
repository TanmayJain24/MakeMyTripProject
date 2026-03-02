package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import utilities.Log;

public class TC003_SearchCabs extends BaseTest {
    @Test
    public void selectTripTypeTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        Assert.assertTrue(cabPage.clickOneWayOutstation(), "One Way Outstation button not selected!");
        Log.info("Trip Type Selected");
    }

    @Test
    public void selectCabsTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);

        cabPage.openCabsPage();
        cabPage.clickOneWayOutstation();

        boolean fromCitySelected = cabPage.selectFromCity("Delhi");
        Assert.assertTrue(fromCitySelected, "From city not set correctly!");

        boolean toCitySelected = cabPage.selectToCity("Manali");
        Assert.assertTrue(toCitySelected, "To city not set correctly!");

        boolean departureDateSelected = cabPage.selectDepartureDate("26", "March 2026");
        Assert.assertTrue(departureDateSelected, "Departure date not set correctly!");

        boolean pickupTimeSelected = cabPage.selectPickupTime("10:30 AM");
        Assert.assertTrue(pickupTimeSelected, "Pickup time not set correctly!");

        cabPage.searchCabs();

        boolean cabTypeSelected = cabPage.selectCabType("SUV");
        Assert.assertTrue(cabTypeSelected, "Cab type not selected correctly!");
    }
}
