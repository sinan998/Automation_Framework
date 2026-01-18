package com.sinan.pages;

import com.sinan.utilities.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        String timeoutValue = ConfigManager.getProperty("timeout");
        long timeout = (timeoutValue != null) ? Long.parseLong(timeoutValue) : 15;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(driver,this);
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.debug("Element görünür: {}", element);
            return true;
        } catch (Exception e) {
            logger.debug("Element görünür değil: {}", element);
            return false;
        }
    }

    public void scrollToElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }



}
