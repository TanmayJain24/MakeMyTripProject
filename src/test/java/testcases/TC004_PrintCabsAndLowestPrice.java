package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import utilities.ExcelUtil;
import utilities.ScreenshotUtility;
import java.util.ArrayList;
import java.util.List;

public class TC004_PrintCabsAndLowestPrice extends BaseTest{
    @Test
    public void printCabsAndLowestPriceTest() throws InterruptedException {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        cabPage.clickCabServices("Outstation One-way");
        cabPage.selectPickupLocation("mumbai, maharashtra");
        cabPage.selectDropLocation("manali, himachal pradesh");
        cabPage.selectPickUpDate("26", "March 2026");
        cabPage.selectPickupTime("10:30 AM");
        cabPage.searchCabs();
        cabPage.selectCabType("SUV");
        cabPage.printLowestCabPrice();
        List<String[]> cabResults = cabPage.getCabListData();
        Assert.assertTrue(!cabResults.isEmpty(), "No cabs found!");
        List<String[]> datatoWrite = new ArrayList<>();
        for (int i = 0; i < cabResults.size(); i++) {
            String[] cab = cabResults.get(i);
            datatoWrite.add(new String[]{
                    String.valueOf(i + 1),
                    cab[0],
                    cab[1],
                    cab[2]
            });
        }
        String[] headers = {"Sr. No.", "Cab Name", "Price", "Rating"};
        String filePath = System.getProperty("user.dir") + "/ExcelData/GoibiboReport.xlsx";
        String sheetName = "CabsList";
        ExcelUtil.writeDynamicDataToExcel(filePath, sheetName, headers, datatoWrite);
        ScreenshotUtility.takeScreenShot(driver, "CabsList_Results");
    }
}