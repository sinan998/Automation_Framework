package com.sinan.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sinan.utilities.ConfigManager;

public class TextBoxPage extends BasePage {

    public TextBoxPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "userName")
    WebElement txtFullName;

    @FindBy(id = "userEmail")
    WebElement txtEmail;

    @FindBy(id = "currentAddress")
    WebElement txtCurrentAddress;

    @FindBy(id = "permanentAddress")
    WebElement txtPermanentAddress;

    @FindBy(id = "submit")
    WebElement btnSubmit;

    @FindBy(id = "output")
    WebElement divOutput;

    @FindBy(id = "name")
    WebElement lblName;

    @FindBy(id = "email")
    WebElement lblEmail;

    @FindBy(css = "p#currentAddress")
    WebElement lblCurrentAddress;

    @FindBy(css = "p#permanentAddress")
    WebElement lblPermanentAddress;

    public void openTextBoxPage() {
        String baseUrl = ConfigManager.getProperty("base_url");
        driver.get(baseUrl + "/text-box");
    }

    public void fillFormAndSubmit(String fullName, String email, String currentAddress, String permanentAddress) {
        sendKeys(txtFullName, fullName);
        sendKeys(txtEmail, email);
        sendKeys(txtCurrentAddress, currentAddress);
        sendKeys(txtPermanentAddress, permanentAddress);

        scrollToElement(btnSubmit);
        click(btnSubmit);
    }

    public boolean verifySubmissionResult(String expectedFullName, String expectedEmail,
            String expectedCurrentAddress, String expectedPermanentAddress) {
        if (!isElementDisplayed(divOutput)) {
            return false;
        }

        boolean nameCorrect = isElementDisplayed(lblName) &&
                lblName.getText().contains(expectedFullName);

        boolean emailCorrect = isElementDisplayed(lblEmail) &&
                lblEmail.getText().contains(expectedEmail);

        boolean currentAddressCorrect = isElementDisplayed(lblCurrentAddress) &&
                lblCurrentAddress.getText().contains(expectedCurrentAddress);

        boolean permanentAddressCorrect = isElementDisplayed(lblPermanentAddress) &&
                lblPermanentAddress.getText().contains(expectedPermanentAddress);

        return nameCorrect && emailCorrect && currentAddressCorrect && permanentAddressCorrect;
    }

    public boolean isOutputDisplayed() {
        return isElementDisplayed(divOutput);
    }

}
