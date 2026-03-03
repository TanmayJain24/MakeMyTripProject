package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;

public class TC005_EnterTravelerDetails extends BaseTest {
    @Test
    public void enterTravelerDetailsTest() {
        CabBookingPage cabPage = new CabBookingPage(driver, wait);
        cabPage.openCabsPage();
        cabPage.clickCabServices("Hourly Rental");
        cabPage.selectPickupLocation("t nagar bus stop");
        cabPage.selectPickUpDate("24", "March 2026");
        cabPage.selectPickupTime("02:00 PM");
        cabPage.selectRentalHours("3 hr");
        cabPage.searchCabs();
        cabPage.selectCabModel("Innova Crysta");
        cabPage.selectFuelType("Diesel");
        cabPage.selectLowestCab();
        cabPage.enterTravelerDetails("Jenny", "Female", "9876543210", "jenny@gmail.com");
        Assert.assertTrue(driver.getPageSource().contains("Jenny"), "Traveler name not entered.");
        Assert.assertTrue(driver.getPageSource().contains("9876543210"), "Traveler mobile not entered.");
        Assert.assertTrue(driver.getPageSource().contains("jenny@gmail.com"), "Traveler email not entered.");
    }
}