package com.sinan.pages;

import com.sinan.utilities.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "userName")
    WebElement txtUsername;

    @FindBy(id = "password")
    WebElement txtPassword;

    @FindBy(id = "login")
    WebElement btnLogin;

    @FindBy(css = "p[id='name']")
    WebElement lblErrorMessage;

    @FindBy(css = "input[class='mr-sm-2 is-invalid form-control']")
    WebElement passwordBoxControl;

    public void openLoginPage() {
        String baseUrl = ConfigManager.getProperty("base_url");
        driver.get(baseUrl + "/login");
    }


    public void performLogin(String user, String pass) {
        sendKeys(txtUsername, user);
        sendKeys(txtPassword,pass);

        scrollToElement(btnLogin);
        click(btnLogin);
    }


    public boolean isInvalidMessageDisplayed() {
        return isElementDisplayed(lblErrorMessage);
    }

    public boolean isPasswordBoxRedNull() {
        return isElementDisplayed(passwordBoxControl);
    }

}
