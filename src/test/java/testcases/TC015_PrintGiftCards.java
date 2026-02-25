package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;



public class TC015_PrintGiftCards extends BaseTest {
    @Test
    public void printCards(){
        GiftCardPage gift = new GiftCardPage(driver , wait);

        gift.getGiftCardTitles();
    }
}
