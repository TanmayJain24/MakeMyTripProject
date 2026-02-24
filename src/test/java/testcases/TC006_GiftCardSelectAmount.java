package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

public class TC006_GiftCardSelectAmount extends BaseTest {
    @Test
    public void selectGiftCardAmountAndConfirm() {
        GiftCardPage gift = new GiftCardPage(driver, wait);
        gift.clickGiftCard();
    }
}
