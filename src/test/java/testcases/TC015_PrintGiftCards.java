package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCardPage;
import utilities.ExcelUtil;
import java.util.List;

public class TC015_PrintGiftCards extends BaseTest {

    @Test
    public void printCards() {
        GiftCardPage gift = new GiftCardPage(driver, wait);
        gift.openGiftCardSection();
        List<String> titles = gift.getGiftCardTitles();
        List<String[]> datatoWrite = new java.util.ArrayList<>();

        for(int i=0; i<titles.size(); i++){
            datatoWrite.add(new String[]{
                String.valueOf(i+1),
                    titles.get(i),
            });
        }
        gift.clickGiftCard();

        Assert.assertTrue(titles.size() > 0, "No gift card titles found!");

        // Write to Excel (output under target/exports)
        String[] headers = {"Sr. No.", "GiftCardTitles"};
        String filePath = System.getProperty("user.dir") + "/ExcelData/GoibiboReport.xlsx";
        String sheetName = "Sheet_3";
        ExcelUtil.writeDynamicDataToExcel(filePath, sheetName, headers, datatoWrite);
    }
}