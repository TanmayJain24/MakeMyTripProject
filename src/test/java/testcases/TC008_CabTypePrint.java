package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import java.util.List;

public class TC008_CabTypePrint extends BaseTest {
    @Test
    public void cabTypePrintTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        boolean cabServiceSelected = cabPage.clickCabServices("Outstation One-way");
        Assert.assertTrue(cabServiceSelected, "Cab Service Type not selected!");
        boolean fromCitySelected = cabPage.selectPickupLocation("delhi cantt");
        Assert.assertTrue(fromCitySelected, "From city not set correctly!");
        boolean toCitySelected = cabPage.selectDropLocation("manali, himachal pradesh");
        Assert.assertTrue(toCitySelected, "To city not set correctly!");
        boolean departureDateSelected = cabPage.selectPickUpDate("26", "March 2026");
        Assert.assertTrue(departureDateSelected, "Departure date not set correctly!");
        boolean pickupTimeSelected = cabPage.selectPickupTime("10:30 AM");
        Assert.assertTrue(pickupTimeSelected, "Pickup time not set correctly!");
        cabPage.searchCabs();
        List<String> typesList = cabPage.getAvailableCarTypes();
        Assert.assertTrue(!typesList.isEmpty(), "List is Empty!");
    }
}
