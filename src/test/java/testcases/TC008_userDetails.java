package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

public class TC008_userDetails extends BaseTest {
    @Test
    public void UserDetails() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver, wait);

        gift.clickGiftCard();
        gift.SelectAmount();
        gift.getEnteredAmount();
        gift.userDetails();

}
}
