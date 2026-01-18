package com.sinan.tests;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.sinan.listeners.TestListener;
import com.sinan.utilities.DriverFactory;
import com.sinan.utilities.FrameworkConstants;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setup() {
        logger.info("Test setup başlatılıyor...");
        DriverFactory.getDriver();
        logger.info("WebDriver başarıyla oluşturuldu");
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Test tearDown başlatılıyor...");
        DriverFactory.closeDriver();
        logger.info("WebDriver kapatıldı");
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public String getScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String destination = FrameworkConstants.SCREENSHOT_PATH + testName + "_" + dateName + ".png";

        File finalDestination = new File(destination);

        File destinationFolder = new File(FrameworkConstants.SCREENSHOT_PATH);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        try {
            FileHandler.copy(source, finalDestination);
            logger.debug("Screenshot kaydedildi: {}", destination);
        } catch (IOException e) {
            logger.error("Screenshot kaydedilirken hata oluştu", e);
        }

        return destination;
    }

    public void logScreenshot(String message) {
        try {
            String screenshotPath = getScreenshot("Success_" + System.currentTimeMillis());
            TestListener.getTest().log(Status.PASS, message,
                    com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath)
                            .build());
        } catch (Exception e) {
            logger.error("Rapora ekran görüntüsü eklenirken hata oluştu", e);
        }
    }

}
