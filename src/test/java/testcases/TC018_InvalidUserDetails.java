package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;
import utilities.ScreenshotUtility;
import utilities.ConfigReader;

public class TC018_InvalidUserDetails extends BaseTest {

    @Test
    public void invalidUserDetailsTest() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver, wait);

        // Read from config
        String name   = ConfigReader.getProperty("invalid.name");
        String mobile = ConfigReader.getProperty("invalid.mobile");
        String email  = ConfigReader.getProperty("invalid.email");

        ScreenshotUtility.takeScreenShot(driver, "InvalidUserDetails");

        String uiError = gift.userDetailsInvalid(name, mobile, email);

        // Assertions
        Assert.assertFalse(name.contains(" "), "Name should not contain space");
        Assert.assertTrue(uiError.length() > 0, "Email error message should be visible");
    }
}