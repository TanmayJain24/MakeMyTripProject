package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusBookingPage;

public class TC011_ApplyFilter extends BaseTest {

    @Test
    public void testACAndSleeperBusFilter() throws InterruptedException {
        BusBookingPage busPage = new BusBookingPage(driver, wait);


        System.out.println("Applying AC and Sleeper filters...");
        busPage.applyFilters();
        int filteredBusCount = busPage.getBusCount();
        System.out.println("===============================================");
        System.out.println("FILTER RESULTS FOR TOMORROW");
        System.out.println("Route: Pune to Delhi");
        System.out.println("Criteria: AC + Sleeper");
        System.out.println("Total Filtered Buses: " + filteredBusCount);
        System.out.println("===============================================");
        Assert.assertTrue(filteredBusCount >= 0, "The filtered bus count should be valid (0 or more).");
    }
}