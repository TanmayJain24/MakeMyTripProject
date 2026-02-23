package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.HotelBookingPage;

import java.util.List;

public class TC_006_Hotel extends BaseTest {

    @Test
    public void searchHotelsInManali() throws InterruptedException {
        // Create page object
        HotelBookingPage hotelPage = new HotelBookingPage(driver);



        // Step 1: Navigate to Hotels section
        hotelPage.openHotelBooking();


        // Step 2: Search hotels with valid dates
        hotelPage.searchHotels("Manali", "2026-03-10", "2026-03-15");

        // Step 3: Capture and display hotel names
        List<String> hotelNames = hotelPage.getHotelNames();
        System.out.println("Hotels found in Manali:");
        for (String name : hotelNames) {
            System.out.println(name);
        }
    }
}
