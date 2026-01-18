package com.sinan.tests;

import static com.sinan.utilities.FrameworkConstants.EXCEL_PATH;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.sinan.pages.LoginPage;
import com.sinan.utilities.ExcelUtils;

public class LoginTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTests.class);

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() {

        ExcelUtils excelUtils = new ExcelUtils(EXCEL_PATH, "Sayfa1");

        return excelUtils.getDataArrayWithoutHeader();
    }

    @Test(dataProvider = "LoginData")
    public void testLoginDDT(String username, String password, String expectedMessage) {

        logger.info("Test Başlıyor -> Kullanıcı: {}", username);

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.openLoginPage();

        loginPage.performLogin(username, password);

        boolean isDisplayed = loginPage.isInvalidMessageDisplayed();

        if (isDisplayed) {
            logScreenshot("Hata Mesajı Görüldü: " + username);
        }

        Assert.assertTrue(isDisplayed, "Beklenen hata mesajı görüntülenmedi!");
    }

    @Test
    public void testLoginWithNullPassword() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.openLoginPage();

        Faker faker = new Faker();
        String myUser = faker.name().username();
        String myPass = "";

        loginPage.performLogin(myUser, myPass);

        boolean nullPasswordBoxControl = loginPage.isPasswordBoxRedNull();
        if (nullPasswordBoxControl) {
            logScreenshot("boş şifre alanı başarıyla görüntülendi.");
        }
        Assert.assertTrue(nullPasswordBoxControl, "şifre null olduğunda kutu kırmızı olmadı");

    }

}
