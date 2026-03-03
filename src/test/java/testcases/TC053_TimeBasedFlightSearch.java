package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;
import utilities.ExcelUtil;
import utilities.Log;
import java.util.ArrayList;
import java.util.List;
import utilities.ScreenshotUtility;

public class TC053_TimeBasedFlightSearch extends BaseTest {
    @Test
    public void verifyTimeBasedFilters() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);
        fp.selectFromCity("Chennai");
        fp.selectToCity("Mumbai");
        fp.selectDate("10", "December 2026");
        fp.travellerSelectAdult("2");
        fp.searchResults();
        boolean afterRes = fp.filterDepartureAfter6PM();
        boolean befoRes = fp.filterArrivalBefore6AM();
        boolean results = afterRes && befoRes;
        ScreenshotUtility.takeScreenShot(driver, "TimeBaseFilterApplied");
        List<String[]> flightData = fp.printAndCollectResults("Late Night Window", "Chennai", "Mumbai");
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
        String sheetName = "TimeBasedResults";
        ExcelUtil.writeDynamicDataToExcel(filePath, sheetName, headers, datatoWrite);
        Log.info("Excel Report generated for TC053 with " + datatoWrite.size() + " filtered flights.");
        Assert.assertTrue(results, "Late Night - Early Morning filters Not Selected successfully");
    }
}
