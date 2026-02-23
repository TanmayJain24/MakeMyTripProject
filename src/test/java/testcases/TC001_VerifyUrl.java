package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC001_VerifyUrl extends BaseTest {
    @Test
    public void verifyUrlTest() {
        String expectedUrl = "https://www.goibibo.com/flights/"; // replace with actual cab page URL
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Cab booking page URL mismatch!");
    }
}