package testcases;

import basetest.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CabBookingPage;
import java.util.List;

public class TC004_CabCarTypePrint extends BaseTest {
    @Test
    public void car_type_list_print() throws InterruptedException {
        CabBookingPage cab = new CabBookingPage(driver, wait);
        cab.openCabsPage();
        cab.selectFromCity("Delhi");
        cab.selectToCity("Manali");

        List<String> typesLi = cab.getAvailableCarTypes();

        System.out.println("\n\nAvailable Car Types : ");
        for(String x: typesLi)
        {
            System.out.println(x);
        }
        Assert.assertFalse(typesLi.isEmpty());
    }
}
