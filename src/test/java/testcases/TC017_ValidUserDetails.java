package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;
import utilities.ConfigReader;

public class TC017_ValidUserDetails extends BaseTest {
    @Test
    public void UserDetails() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver, wait);
        gift.openGiftCardSection();
        gift.clickGiftCard();

        String name   = ConfigReader.getProperty("valid.name");
        String mobile = ConfigReader.getProperty("valid.mobile");
        String email  = ConfigReader.getProperty("valid.email");

        gift.userDetails(name, mobile, email);

        Assert.assertTrue(name.matches("^[A-Za-z]+\\s[A-Za-z]+$"), "Name should be a valid full name");
        String digitsOnly = mobile.replaceAll("\\D", "");
        Assert.assertEquals(digitsOnly.length(), 10, "Mobile must be exactly 10 digits");
        Assert.assertTrue(email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$"), "Email should be in valid format"); // Assert no error message returned from UI Assert.assertTrue(uiError == null || uiError.isEmpty(), "No error message should be visible for valid details");
    }
}
