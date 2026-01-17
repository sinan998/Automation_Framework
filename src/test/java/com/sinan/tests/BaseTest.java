package com.sinan.tests;

import com.aventstack.extentreports.Status;
import com.sinan.listeners.TestListener;
import com.sinan.utilities.ConfigManager;
import com.sinan.utilities.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.openqa.selenium.io.FileHandler;

public class BaseTest {


    @BeforeMethod
    public void setup(){

        DriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDown(){
        DriverFactory.closeDriver();
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public String getScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String destination = System.getProperty("user.dir") + "/reports/screenshots/" + testName + "_" + dateName + ".png";

        File finalDestination = new File(destination);

        // --- EKLENEN KISIM: Klasör Kontrolü ---
        // Eğer 'screenshots' klasörü yoksa oluştur
        File destinationFolder = new File(System.getProperty("user.dir") + "/reports/screenshots/");
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }
        // -------------------------------------

        try {
            FileHandler.copy(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destination;
    }

    public void logScreenshot(String message){
        try{
            String screenshotPath = getScreenshot("Success_" + System.currentTimeMillis());
            TestListener.getTest().log(Status.PASS, message,
                    com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }catch (Exception e) {
            System.out.println("Rapora ekran görüntüsü eklenirken hata oluştu: " + e.getMessage());
        }
    }

}
