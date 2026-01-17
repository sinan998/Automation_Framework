package com.sinan.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sinan.tests.BaseTest;
import com.sinan.utilities.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{
    // Rapor objeleri
    private static ExtentReports extent = ExtentReportManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        // Test her başladığında raporda yeni bir kayıt aç
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Başarılı: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // 1. Hata mesajını rapora ekle
        test.get().fail(result.getThrowable());

        // 2. Screenshot al ve rapora ekle
        try {
            // Test sınıfındaki driver'ı alıyoruz
            Object currentClass = result.getInstance();
            WebDriver driver = ((BaseTest) currentClass).getDriver();

            // Screenshot metodunu çağırıyoruz
            if (driver != null) {
                String screenshotPath = ((BaseTest) currentClass).getScreenshot(result.getMethod().getMethodName());
                // Rapora resmi ekle
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Raporu kaydet ve kapat
        if (extent != null) {
            extent.flush();
        }
    }

    public static synchronized ExtentTest getTest() {
        return test.get();
    }


















}
