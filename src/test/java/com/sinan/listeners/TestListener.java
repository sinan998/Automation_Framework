package com.sinan.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sinan.tests.BaseTest;
import com.sinan.utilities.ExtentReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{
    
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static ExtentReports extent = ExtentReportManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test başlatılıyor: {}", result.getMethod().getMethodName());
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test başarılı: {}", result.getMethod().getMethodName());
        test.get().log(Status.PASS, "Test Başarılı: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test başarısız: {}", result.getMethod().getMethodName(), result.getThrowable());
        test.get().fail(result.getThrowable());

        try {
            Object currentClass = result.getInstance();
            WebDriver driver = ((BaseTest) currentClass).getDriver();

            if (driver != null) {
                String screenshotPath = ((BaseTest) currentClass).getScreenshot(result.getMethod().getMethodName());
                logger.debug("Screenshot alındı: {}", screenshotPath);
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Screenshot alınırken hata oluştu", e);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test suite tamamlandı. Rapor kaydediliyor...");
        if (extent != null) {
            extent.flush();
            logger.info("Rapor başarıyla kaydedildi");
        }
    }

    public static synchronized ExtentTest getTest() {
        return test.get();
    }


















}
