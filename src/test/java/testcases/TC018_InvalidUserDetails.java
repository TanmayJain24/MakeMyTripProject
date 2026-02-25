package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

public class TC018_InvalidUserDetails extends BaseTest {

    @Test
    public void invalidUserDetailsTest() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver, wait);

        String name = "fskffksfsg";                // Invalid: no space
        String mobile = "5594993902--2222";        // Invalid: more than 10 digits
        String email = "tanishkyadav6263@gmail";

        String uiError = gift.userDetailsInvalid(name, mobile, email);

        Assert.assertFalse(name.contains(" "), "Name should not contain space");
        String digitsOnly = mobile.replaceAll("\\D", "");
        Assert.assertTrue(digitsOnly.length() > 10, "Mobile must be more than 10 digits");
        Assert.assertTrue(uiError.length() > 0, "Email error message should be visible");
    }
}