package testcases;

import basetest.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

import java.time.Duration;

public class TC007_selectPriceShowsInEnterAmount extends BaseTest {
    @Test
    public void selectPriceShow() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver , wait);
        gift.clickGiftCard();
        gift.SelectAmount();

        String actual = gift.getEnteredAmount();
        System.out.println(actual);
        Assert.assertEquals(actual, "10000", "Entered amount is incorrect!");
    }
}
