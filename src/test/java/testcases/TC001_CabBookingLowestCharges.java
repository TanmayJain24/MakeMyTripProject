package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC001_CabBookingLowestCharges extends BaseTest {
    @Test
    public void TC001_CabBookingLowestCharges() {
        CabBookingPage cabPage = new CabBookingPage(driver);
        cabPage.enterTripDetails("Delhi", "Manali", "23/12/2019", "10:00", "SUV");
        cabPage.searchCabs();
        System.out.println("Lowest charges: " + cabPage.getLowestCharges());
    }
}
