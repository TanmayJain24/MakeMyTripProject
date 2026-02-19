package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC001_CabBookingLowestCharges extends BaseTest {
    @Test
    public void bookCabTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        cabPage.selectFromCity("Chennai");
        cabPage.selectToCity("Bengaluru");
        cabPage.selectDepartureDate("Thu Feb 26 2026");
        cabPage.selectReturnDate("Sat Feb 28 2026");
        cabPage.selectPickupTime("10", "30", "AM");
        cabPage.searchCabs();
        cabPage.printLowestCabPrice();
    }
}
