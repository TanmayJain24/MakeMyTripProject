package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import utilities.Log;
import utilities.ScreenshotUtility;

public class TC002_OpenCabsPage extends BaseTest {
    @Test
    public void openCabsPageTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        ScreenshotUtility.takeScreenShot(driver, "Navigate_CabsPage");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Cab Booking - Outstation cabs";
        Assert.assertNotNull(actualTitle);
        Assert.assertTrue(actualTitle.contains(expectedTitle), "Cab booking page URL mismatch!");
    }
}