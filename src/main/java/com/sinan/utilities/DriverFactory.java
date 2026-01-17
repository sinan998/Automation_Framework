package com.sinan.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    public static WebDriver getDriver(){
        if (driverPool.get() == null){
            createDriver();
        }
        return driverPool.get();
    }

    private static void createDriver() {

        String browser = ConfigManager.getProperty("browser").toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();

                // Headless ayarı
                if (ConfigManager.getProperty("headless").equals("true")) {
                    options.addArguments("--headless");
                    options.addArguments("--window-size=1920,1080"); // Headless modda boyut önemlidir
                }

                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Geçersiz tarayıcı: " + browser);
        }

        String timeoutValue = ConfigManager.getProperty("timeout");

        long timeout = (timeoutValue != null) ? Long.parseLong(timeoutValue) : 15;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();

        // Oluşan driver'ı thread'in kasasına koyuyoruz
        driverPool.set(driver);


    }

    public static void closeDriver() {
        // Kasada driver varsa kapat ve kasayı boşalt
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }

}


