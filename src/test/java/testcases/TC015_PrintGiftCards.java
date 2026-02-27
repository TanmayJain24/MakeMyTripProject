package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;
import utilities.ExcelUtility;
// import utilities.CsvUtility; // if you prefer CSV

import java.util.List;

public class TC015_PrintGiftCards extends BaseTest {

    @Test
    public void printCards() {
        GiftCardPage gift = new GiftCardPage(driver, wait);

        // Navigate (if needed for your app flow)

        gift.openGiftCardSection();
        List<String> titles = gift.getGiftCardTitles();
        gift.clickGiftCard();



        Assert.assertTrue(titles.size() > 0, "No gift card titles found!");

        // Write to Excel (output under target/exports)
        String path = ExcelUtility.writeGiftCardTitles(titles, "target/exports", "gift_cards");
        System.out.println("Gift card titles exported to: " + path);


    }
}