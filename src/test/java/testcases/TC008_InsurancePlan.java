package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.InsurancePage;
import utilities.ScreenshotUtility;
import utilities.Log; // Using your Log utility
import java.time.Duration;

public class TC008_InsurancePlan extends BaseTest {
    @Test
    public void testInsurancePlanWorkflow() {
        Log.info("Starting Insurance Plan Workflow Test Case.");
        InsurancePage insurancePage = new InsurancePage(driver, Duration.ofSeconds(20));
        insurancePage.clickInsuranceTab();
        insurancePage.selectThailand();
        insurancePage.addOneTraveller();
        insurancePage.clickViewPlans();
        insurancePage.scrollToBottom();
        ScreenshotUtility.takeScreenShot(driver, "InsurancePlan");
        Log.info("Insurance Test Case 1 completed successfully.");
    }
}