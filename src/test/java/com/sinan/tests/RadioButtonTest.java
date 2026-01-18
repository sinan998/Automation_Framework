package com.sinan.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sinan.pages.RadioButtonPage;

public class RadioButtonTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(RadioButtonTest.class);

    @Test
    public void testYesRadioButton() {
        RadioButtonPage radioButtonPage = new RadioButtonPage(getDriver());

        radioButtonPage.openRadioButtonPage();

        logger.info("Yes radio button tıklanıyor...");
        radioButtonPage.clickYes();

        boolean isYesSelected = radioButtonPage.isYesSelected();
        boolean isOutputDisplayed = radioButtonPage.isOutputDisplayed();
        boolean outputContainsYes = radioButtonPage.verifyOutputContains("Yes");

        if (isYesSelected && isOutputDisplayed && outputContainsYes) {
            logScreenshot("Yes radio button başarıyla seçildi");
        }

        Assert.assertTrue(isYesSelected, "Yes radio button seçilmedi!");
        Assert.assertTrue(isOutputDisplayed, "Output mesajı görüntülenmedi!");
        Assert.assertTrue(outputContainsYes, "Output mesajı 'Yes' içermiyor!");
    }

    @Test
    public void testImpressiveRadioButton() {
        RadioButtonPage radioButtonPage = new RadioButtonPage(getDriver());

        radioButtonPage.openRadioButtonPage();

        logger.info("Impressive radio button tıklanıyor...");
        radioButtonPage.clickImpressive();

        boolean isImpressiveSelected = radioButtonPage.isImpressiveSelected();
        boolean isOutputDisplayed = radioButtonPage.isOutputDisplayed();
        boolean outputContainsImpressive = radioButtonPage.verifyOutputContains("Impressive");

        if (isImpressiveSelected && isOutputDisplayed && outputContainsImpressive) {
            logScreenshot("Impressive radio button başarıyla seçildi");
        }

        Assert.assertTrue(isImpressiveSelected, "Impressive radio button seçilmedi!");
        Assert.assertTrue(isOutputDisplayed, "Output mesajı görüntülenmedi!");
        Assert.assertTrue(outputContainsImpressive, "Output mesajı 'Impressive' içermiyor!");
    }

    @Test
    public void testNoRadioButtonDisabled() {
        RadioButtonPage radioButtonPage = new RadioButtonPage(getDriver());

        radioButtonPage.openRadioButtonPage();

        logger.info("No radio button durumu kontrol ediliyor...");
        boolean isNoEnabled = radioButtonPage.isNoEnabled();

        logger.info("No radio button enabled durumu: {}", isNoEnabled);

        Assert.assertFalse(isNoEnabled, "No radio button enabled olmamalı!");
    }

    @Test
    public void testRadioButtonSwitching() {
        RadioButtonPage radioButtonPage = new RadioButtonPage(getDriver());

        radioButtonPage.openRadioButtonPage();

        logger.info("Yes radio button seçiliyor...");
        radioButtonPage.clickYes();
        Assert.assertTrue(radioButtonPage.isYesSelected(), "Yes radio button seçilmedi!");

        logger.info("Impressive radio button seçiliyor...");
        radioButtonPage.clickImpressive();
        Assert.assertTrue(radioButtonPage.isImpressiveSelected(), "Impressive radio button seçilmedi!");
        Assert.assertFalse(radioButtonPage.isYesSelected(), "Yes radio button hala seçili!");

        boolean isOutputDisplayed = radioButtonPage.isOutputDisplayed();
        boolean outputContainsImpressive = radioButtonPage.verifyOutputContains("Impressive");

        if (isOutputDisplayed && outputContainsImpressive) {
            logScreenshot("Radio button değiştirme işlemi başarılı");
        }

        Assert.assertTrue(isOutputDisplayed, "Output mesajı görüntülenmedi!");
        Assert.assertTrue(outputContainsImpressive, "Output mesajı 'Impressive' içermiyor!");
    }

}
