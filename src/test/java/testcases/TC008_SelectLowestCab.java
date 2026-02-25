package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC008_SelectLowestCab extends BaseTest {
    @Test
    public void selectLowestCabTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.selectLowestCab();
    }
}
