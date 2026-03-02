package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import utilities.ScreenshotUtility;

public class TC006_ValidateDate extends BaseTest {
    @Test
    public void validateDateTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        cabPage.clickOneWayOutstation();
        cabPage.selectFromCity("Delhi");
        cabPage.selectToCity("Manali");
        cabPage.selectDepartureDate("26", "March 2026");
        cabPage.selectPickupTime("10:30 AM");
        cabPage.searchCabs();
        cabPage.selectCabType("SUV");
        cabPage.selectLowestCab();
        String expectedDate = "Thu Mar 26 2026";
        Assert.assertTrue(cabPage.validateDate(expectedDate), "Date mismatch! Expected is : " + expectedDate);
        ScreenshotUtility.takeScreenShot(driver, "BookingPage");
    }
}