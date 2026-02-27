package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;
import utilities.ScreenshotUtility;

public class TC016_SelectPriceShowsInEnterAmount extends BaseTest {
    @Test
    public void selectPriceShow() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver , wait);
        gift.openGiftCardSection();
        gift.clickGiftCard();
        gift.SelectAmount();
        String actual = gift.getEnteredAmount();
        ScreenshotUtility.takeScreenShot(driver, "ShowGiftAmount");
        System.out.println(actual);
        Assert.assertEquals(actual, "10000", "Entered amount is incorrect!");
    }
}
