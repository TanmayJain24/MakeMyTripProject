package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC001_VerifyUrlAndTitle extends BaseTest {
    @Test
    public void verifyUrlAndTitleTest() {
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://www.goibibo.com/flights/";
        Assert.assertEquals(actualUrl, expectedUrl, "Cab booking page URL mismatch!");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Goibibo";
        Assert.assertNotNull(actualTitle);
        Assert.assertTrue(actualTitle.contains(expectedTitle), "Cab booking page URL mismatch!");
    }
}