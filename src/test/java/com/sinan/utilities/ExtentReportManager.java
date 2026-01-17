package com.sinan.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.sinan.utilities.FrameworkConstants.REPORT_PATH;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports createInstance() {
        Locale.setDefault(Locale.ENGLISH);

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String fileName = REPORT_PATH + dateName + ".html";

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORT_PATH);

        htmlReporter.config().setTheme(Theme.DARK); // Koyu tema candır :)
        htmlReporter.config().setDocumentTitle("Otomasyon Raporu");
        htmlReporter.config().setReportName("Test Sonuçları");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Tester", "Sinan");
        extent.setSystemInfo("Environment", "Test Ortamı");
        extent.setSystemInfo("Browser", ConfigManager.getProperty("browser"));

        return extent;
    }















}
