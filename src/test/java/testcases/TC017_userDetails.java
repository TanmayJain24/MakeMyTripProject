package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

public class TC017_userDetails extends BaseTest {
    @Test
    public void UserDetails() throws InterruptedException {
        GiftCardPage gift = new GiftCardPage(driver, wait);
        gift.userDetails();
    }
}
