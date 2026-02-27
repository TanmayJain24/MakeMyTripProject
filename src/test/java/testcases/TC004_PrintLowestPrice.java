package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import utilities.ScreenshotUtility;

public class TC004_PrintLowestPrice extends BaseTest{
    @Test
    public void printLowestPriceTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        cabPage.clickOneWayOutstation();
        cabPage.selectFromCity("Delhi");
        cabPage.selectToCity("Manali");
        cabPage.selectDepartureDate("26", "March 2026");
        cabPage.selectPickupTime("10:30 AM");
        cabPage.searchCabs();
        cabPage.selectCabType("SUV");
        cabPage.printLowestCabPrice();
        ScreenshotUtility.takeScreenShot(driver, "CabsList_Results");
    }
}