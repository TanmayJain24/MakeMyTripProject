package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;
import utilities.ExcelUtil; // Using your provided utility
import utilities.Log;
import java.util.ArrayList;
import java.util.List;

public class TC019_CheapestFlightSearchResults extends BaseTest {
    @Test
    public void cheapestFlightSearchResultsTest() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);
        boolean fromStatus = fp.selectFromCity("Chennai");
        Assert.assertTrue(fromStatus, "Departure city 'Chennai' selection failed!");
        boolean toStatus = fp.selectToCity("Mumbai");
        Assert.assertTrue(toStatus, "Destination city 'Mumbai' selection failed!");
        boolean dateStatus = fp.selectDate("12", "October 2026");
        Assert.assertTrue(dateStatus, "Date selection failed!");
        String adultCount = fp.travellerSelectAdult("3");
        Assert.assertEquals(adultCount, "3", "Adult traveller count mismatch!");
        fp.travellerSelectChild("1");
        fp.travellerSelectInfant("0");
        String selectedClass = fp.selectClass("First Class");
        Assert.assertTrue(selectedClass.toLowerCase().contains("first class"),
                "Expected 'First Class' but found: " + selectedClass);
        fp.searchResults();
        int totalResults = fp.getResultsCount();
        Assert.assertTrue(totalResults > 0, "Flight search returned 0 results!");
        List<String[]> flightData = fp.printAndCollectResults("First Class", "Chennai", "Mumbai");

        List<String[]> datatoWrite = new ArrayList<>();
        for (int i = 0; i < flightData.size(); i++) {
            String[] flight = flightData.get(i);
            datatoWrite.add(new String[]{
                    String.valueOf(i + 1),
                    flight[0], flight[1], flight[2], flight[3], flight[4]
            });
        }
        String[] headers = {"Sr. No.", "Flight Name", "From", "To", "Time", "Price"};
        String filePath = System.getProperty("user.dir") + "/ExcelData/GoibiboReport.xlsx";
        String sheetNm = "CheapestFlights";
        ExcelUtil.writeDynamicDataToExcel(filePath, sheetNm, headers, datatoWrite);
        Log.info("Excel Report generated with " + datatoWrite.size() + " flights.");
    }
}