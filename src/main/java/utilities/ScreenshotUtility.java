package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtility {
    public static void takeScreenShot(WebDriver driver, String name) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        try {
            FileUtils.copyFile(ts.getScreenshotAs(OutputType.FILE), new File(System.getProperty("user.dir") + "/Screenshots/" + name + ".jpeg"));
            System.out.println(name + ": Screenshot has been captured.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
