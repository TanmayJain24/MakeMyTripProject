package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC004_SelectTripType extends BaseTest {
    @Test
    public void selectTripTypeTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        Assert.assertTrue(cabPage.clickOneWayOutstation());
    }
}