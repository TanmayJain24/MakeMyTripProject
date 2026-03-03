package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC007_ValidateSpecialRequest extends BaseTest {
    @Test
    public void validateSpecialRequestTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        cabPage.clickCabServices("Hourly Rental");
        cabPage.selectPickupLocation("t nagar bus stop");
        cabPage.selectPickUpDate("24", "March 2026");
        cabPage.selectPickupTime("02:00 PM");
        cabPage.selectRentalHours("3 hr");
        cabPage.searchCabs();
        cabPage.selectCabModel("Innova Crysta");
        cabPage.selectFuelType("Diesel");
        cabPage.selectLowestCab();
        boolean result = cabPage.selectSpecialRequestAndValidatePrice("Roof Carrier");
        Assert.assertTrue(result, "Price should increase after selecting Special Request");
    }
}