package testcases;

import basetest.BaseTest;
import org.testng.annotations.Test;
import pageObjects.InsurancePage;

public class P1_InsurancePlan extends BaseTest {

    @Test
    public void testInsurancePlanWorkflow() {
        InsurancePage insurancePage = new InsurancePage(driver, wait);
        insurancePage.clickInsuranceTab();
        insurancePage.selectThailand();
        insurancePage.addOneTraveller();
        insurancePage.clickViewPlans();
        insurancePage.scrollToBottom();

        System.out.println("Insurance Test Case 1 completed successfully.");
    }
}