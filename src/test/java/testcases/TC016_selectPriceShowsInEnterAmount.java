package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

public class TC016_selectPriceShowsInEnterAmount extends BaseTest { // change name
    @Test
    public void selectPriceShow() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver , wait);
        gift.clickGiftCard();
        gift.selectAmount();
         String expectedGiftAmt = "10000";
        String actual = gift.getEnteredAmount();
        System.out.println(actual);
        Assert.assertEquals(actual, expectedGiftAmt, "Entered amount is incorrect!");
    }
}
