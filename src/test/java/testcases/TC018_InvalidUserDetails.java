package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;
import utilities.ScreenshotUtility;

public class TC018_InvalidUserDetails extends BaseTest {
    @Test
    public void invalidUserDetailsTest() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver, wait);
        ScreenshotUtility.takeScreenShot(driver, "InvalidUserDetails");
        String name = "JohnD";
        String mobile = "9876543210";
        String email = "johngmail";
        String uiError = gift.userDetailsInvalid(name, mobile, email);
        Assert.assertFalse(name.contains(" "), "Name should not contain space");
        Assert.assertTrue(uiError.length() > 0, "Email error message should be visible");
    }
}