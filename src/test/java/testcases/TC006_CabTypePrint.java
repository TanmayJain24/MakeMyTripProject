package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import java.util.List;

public class TC006_CabTypePrint extends BaseTest {
    @Test
    public void car_type_list_print() throws InterruptedException {
        CabBookingPage cab = new CabBookingPage(driver, wait);
        List<String> typesList = cab.getAvailableCarTypes();
        System.out.println("\n\nAvailable Car Types : ");
        for(String cabType: typesList) {
            System.out.println(cabType);
        }
        Assert.assertFalse(typesList.isEmpty(), "List is Empty!");
    }
}
