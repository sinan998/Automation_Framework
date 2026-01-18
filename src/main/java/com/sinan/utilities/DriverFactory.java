package com.sinan.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    public static WebDriver getDriver(){
        if (driverPool.get() == null){
            createDriver();
        }
        return driverPool.get();
    }

    private static void createDriver() {
        String browser = ConfigManager.getProperty("browser").toLowerCase();
        logger.info("WebDriver oluşturuluyor: {}", browser);
        WebDriver driver;

        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();

                if (ConfigManager.getProperty("headless").equals("true")) {
                    logger.info("Chrome headless modda başlatılıyor");
                    options.addArguments("--headless");
                    options.addArguments("--window-size=1920,1080");
                }

                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                logger.info("ChromeDriver başarıyla oluşturuldu");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                logger.info("FirefoxDriver başarıyla oluşturuldu");
                break;
            case "edge":
                driver = new EdgeDriver();
                logger.info("EdgeDriver başarıyla oluşturuldu");
                break;
            default:
                logger.error("Geçersiz tarayıcı: {}", browser);
                throw new RuntimeException("Geçersiz tarayıcı: " + browser);
        }

        String timeoutValue = ConfigManager.getProperty("timeout");
        long timeout = (timeoutValue != null) ? Long.parseLong(timeoutValue) : 15;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();
        logger.debug("WebDriver timeout ayarlandı: {} saniye", timeout);

        driverPool.set(driver);
        logger.debug("WebDriver ThreadLocal'a eklendi");
    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            logger.info("WebDriver kapatılıyor...");
            driverPool.get().quit();
            driverPool.remove();
            logger.info("WebDriver başarıyla kapatıldı");
        } else {
            logger.warn("Kapatılacak WebDriver bulunamadı");
        }
    }

}


