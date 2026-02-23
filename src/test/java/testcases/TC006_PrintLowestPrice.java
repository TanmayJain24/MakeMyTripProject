package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC006_PrintLowestPrice extends BaseTest{
    @Test
    public void printLowestPriceTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.printLowestCabPrice();
    }
}
