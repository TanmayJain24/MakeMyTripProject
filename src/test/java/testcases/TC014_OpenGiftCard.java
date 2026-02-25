package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

public class TC014_OpenGiftCard extends BaseTest {
   @Test
    public void  openGift(){
        GiftCardPage gift = new GiftCardPage(driver , wait);
        gift.openGiftCardSection();
    }
}