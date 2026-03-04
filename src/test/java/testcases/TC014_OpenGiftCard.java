package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;

public class TC014_OpenGiftCard extends BaseTest {
   @Test
    public void  openGift(){
        GiftCardPage gift = new GiftCardPage(driver , wait);
        gift.openGiftCardSection();
       String actualTitle = driver.getTitle();
       String expectedTitle = "Gift Cards";
       Assert.assertTrue(actualTitle.contains(expectedTitle), "Page title mismatch. Expected 'Gift Cards' but found: " + actualTitle);
       String currentUrl = driver.getCurrentUrl();
       String expectedUrl = "/gift-cards";
       Assert.assertTrue(currentUrl.contains(expectedUrl), "URL does not contain 'giftcard'. Current URL: " + currentUrl);
    }
}