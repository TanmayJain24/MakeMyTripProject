package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import utilities.Log;

public class TC004_SelectTripType extends BaseTest {
    @Test
    public void selectTripTypeTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        Assert.assertTrue(cabPage.clickOneWayOutstation(), "One Way Outstation button not selected!");
        Log.info("Trip Type Selected");
    }
}