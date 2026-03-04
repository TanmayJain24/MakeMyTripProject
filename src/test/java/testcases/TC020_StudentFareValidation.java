package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;
import utilities.ScreenshotUtility;

public class TC020_StudentFareValidation extends BaseTest {
    @Test
    public void validateStudentFareSelectionTest() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);
        fp.selectFromCity("Delhi");
        fp.selectToCity("Mumbai");
        fp.selectDate("15", "December 2026");
        fp.selectFareType("Student");
        fp.travellerSelectAdult("4");
        fp.searchResults();
        ScreenshotUtility.takeScreenShot(driver, "StudentFareApplied");
        boolean results = fp.confirmSelection();
        Assert.assertTrue(results, "Student Fare Not Selected");
    }
}