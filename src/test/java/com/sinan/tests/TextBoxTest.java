package com.sinan.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.sinan.pages.TextBoxPage;

public class TextBoxTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(TextBoxTest.class);

    @Test
    public void testFillFormAndVerifyResult() {
        TextBoxPage textBoxPage = new TextBoxPage(getDriver());

        textBoxPage.openTextBoxPage();

        Faker faker = new Faker();
        String fullName = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String currentAddress = faker.address().fullAddress();
        String permanentAddress = faker.address().secondaryAddress();

        logger.info("Test Başlıyor -> Full Name: {}", fullName);

        textBoxPage.fillFormAndSubmit(fullName, email, currentAddress, permanentAddress);

        boolean isResultCorrect = textBoxPage.verifySubmissionResult(
                fullName, email, currentAddress, permanentAddress);

        if (isResultCorrect) {
            logScreenshot("Form başarıyla gönderildi ve sonuçlar doğrulandı: " + fullName);
        }

        Assert.assertTrue(isResultCorrect, "Form gönderimi sonrası sonuçlar beklenen değerlerle eşleşmedi!");
    }

    @Test
    public void testFillFormWithSpecificData() {
        TextBoxPage textBoxPage = new TextBoxPage(getDriver());

        textBoxPage.openTextBoxPage();

        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String currentAddress = "123 Main Street, New York, NY 10001";
        String permanentAddress = "456 Oak Avenue, Los Angeles, CA 90001";

        logger.info("Test Başlıyor -> Full Name: {}", fullName);

        textBoxPage.fillFormAndSubmit(fullName, email, currentAddress, permanentAddress);

        boolean isOutputDisplayed = textBoxPage.isOutputDisplayed();

        if (isOutputDisplayed) {
            logScreenshot("Output bölümü başarıyla görüntülendi: " + fullName);
        }

        Assert.assertTrue(isOutputDisplayed, "Submit sonrası output bölümü görüntülenmedi!");

        boolean isResultCorrect = textBoxPage.verifySubmissionResult(
                fullName, email, currentAddress, permanentAddress);

        Assert.assertTrue(isResultCorrect, "Form gönderimi sonrası sonuçlar beklenen değerlerle eşleşmedi!");

    }

    @Test
    public void testOutputDisplayedAfterSubmit() {
        TextBoxPage textBoxPage = new TextBoxPage(getDriver());

        textBoxPage.openTextBoxPage();

        Faker faker = new Faker();
        String fullName = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String currentAddress = faker.address().fullAddress();
        String permanentAddress = faker.address().secondaryAddress();

        textBoxPage.fillFormAndSubmit(fullName, email, currentAddress, permanentAddress);

        boolean isOutputDisplayed = textBoxPage.isOutputDisplayed();

        if (isOutputDisplayed) {
            logScreenshot("Output bölümü başarıyla görüntülendi");
        }

        Assert.assertTrue(isOutputDisplayed, "Submit sonrası output bölümü görüntülenmedi!");

    }

    @Test
    public void testBoundaryValues() {
        logger.info("Boundary values testi başlatılıyor...");
        TextBoxPage textBoxPage = new TextBoxPage(getDriver());

        logger.info("TextBox sayfası açılıyor...");
        textBoxPage.openTextBoxPage();

        Faker faker = new Faker();
        logger.info("500 karakterlik uzun metinler oluşturuluyor...");

        StringBuilder longFullNameBuilder = new StringBuilder();
        while (longFullNameBuilder.length() < 500) {
            longFullNameBuilder.append(faker.lorem().sentence()).append(" ");
        }
        String longFullName = longFullNameBuilder.toString().substring(0, 500);
        logger.info("Full Name metni oluşturuldu. Uzunluk: {} karakter", longFullName.length());

        StringBuilder longCurrentAddressBuilder = new StringBuilder();
        while (longCurrentAddressBuilder.length() < 500) {
            longCurrentAddressBuilder.append(faker.lorem().sentence()).append(" ");
        }
        String longCurrentAddress = longCurrentAddressBuilder.toString().substring(0, 500);
        logger.info("Current Address metni oluşturuldu. Uzunluk: {} karakter", longCurrentAddress.length());

        StringBuilder longPermanentAddressBuilder = new StringBuilder();
        while (longPermanentAddressBuilder.length() < 500) {
            longPermanentAddressBuilder.append(faker.lorem().sentence()).append(" ");
        }
        String longPermanentAddress = longPermanentAddressBuilder.toString().substring(0, 500);
        logger.info("Permanent Address metni oluşturuldu. Uzunluk: {} karakter", longPermanentAddress.length());

        String email = faker.internet().emailAddress();
        logger.info("Email oluşturuldu: {}", email);

        logger.info("Form alanlarına uzun metinler giriliyor...");
        textBoxPage.fillFormAndSubmit(longFullName, email, longCurrentAddress, longPermanentAddress);
        logger.info("Form başarıyla gönderildi");

        logger.info("Output bölümünün görünür olup olmadığı kontrol ediliyor...");
        boolean isOutputDisplayed = textBoxPage.isOutputDisplayed();
        Assert.assertTrue(isOutputDisplayed, "Submit sonrası output bölümü görüntülenmedi!");

        logger.info("Girilen uzun metinlerin output'ta birebir aynı görünüp görünmediği doğrulanıyor...");
        boolean isResultCorrect = textBoxPage.verifySubmissionResult(
                longFullName, email, longCurrentAddress, longPermanentAddress);

        if (isResultCorrect) {
            logger.info("Uzun metinler output'ta kesilmeden görüntülendi. Doğrulama başarılı!");
            logScreenshot("Boundary values testi başarılı - Uzun metinler doğrulandı");
        } else {
            logger.error("Uzun metinler output'ta eksik veya farklı görüntüleniyor!");
        }

        Assert.assertTrue(isResultCorrect, "Girilen uzun metinler output'ta birebir aynı görünmedi!");

    }

}
