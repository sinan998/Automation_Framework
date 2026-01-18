package com.sinan.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sinan.utilities.ConfigManager;

public class RadioButtonPage extends BasePage {

    public RadioButtonPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "yesRadio")
    WebElement rdbYes;

    @FindBy(id = "impressiveRadio")
    WebElement rdbImpressive;

    @FindBy(id = "noRadio")
    WebElement rdbNo;

    @FindBy(css = "span.text-success")
    WebElement lblOutput;

    @FindBy(css = "label[for='yesRadio']")
    WebElement lblYes;

    @FindBy(css = "label[for='impressiveRadio']")
    WebElement lblImpressive;

    @FindBy(css = "label[for='noRadio']")
    WebElement lblNo;

    public void openRadioButtonPage() {
        String baseUrl = ConfigManager.getProperty("base_url");
        driver.get(baseUrl + "/radio-button");
    }

    public void clickYes() {
        scrollToElement(lblYes);
        click(lblYes);
    }

    public void clickImpressive() {
        scrollToElement(lblImpressive);
        click(lblImpressive);
    }

    public void clickNo() {
        scrollToElement(lblNo);
        click(lblNo);
    }

    public boolean isYesSelected() {
        return rdbYes.isSelected();
    }

    public boolean isImpressiveSelected() {
        return rdbImpressive.isSelected();
    }

    public boolean isNoSelected() {
        return rdbNo.isSelected();
    }

    public boolean isNoEnabled() {
        return rdbNo.isEnabled();
    }

    public String getOutputText() {
        if (isElementDisplayed(lblOutput)) {
            return lblOutput.getText();
        }
        return "";
    }

    public boolean isOutputDisplayed() {
        return isElementDisplayed(lblOutput);
    }

    public boolean verifyOutputContains(String expectedText) {
        String outputText = getOutputText();
        return outputText.contains(expectedText);
    }

}
