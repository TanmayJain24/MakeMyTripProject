package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC002_VerifyTitle extends BaseTest {
    @Test
    public void verifyTitleTest() {
        String actualTitle = driver.getTitle();
        String expectedTitle = "Goibibo";
        Assert.assertTrue(actualTitle.contains(expectedTitle), "Cab booking page URL mismatch!");
    }
}