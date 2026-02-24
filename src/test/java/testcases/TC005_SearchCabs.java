package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC005_SearchCabs extends BaseTest {
    @Test
    public void selectCabsTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);

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
