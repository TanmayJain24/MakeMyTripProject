package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.FlightBookingPage;

public class TC052_StudentFareValidation extends BaseTest {
    @Test
    public void validateStudentFareSelection() {
        FlightBookingPage fp = new FlightBookingPage(driver, wait);

        fp.selectFromCity("Delhi");
        fp.selectToCity("Mumbai");
        fp.selectDate(driver, "15", "December 2026");
        fp.selectFareType("Student");
        fp.travellerSelectAdult(driver,"1");


        // Interacting with the top bar Fare Type

        fp.searchResults(driver);


        // Assert that the results page confirms student benefit (usually via a badge or banner)
        boolean results = fp.confirmSelection();
        Assert.assertTrue(results, "Student Fare Not Selected");
    }
}