package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import utilities.ScreenshotUtility;

public class TC008_ValidateDate extends BaseTest {
    @Test
    public void validateDateTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        String expectedDate = "Thu Mar 26 2026";
        Assert.assertTrue(cabPage.validateDate(expectedDate), "Date mismatch! Expected is : " + expectedDate);
        ScreenshotUtility.takeScreenShot(driver, "BookingPage");
    }
}