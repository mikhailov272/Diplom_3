package ru.praktikum.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForgotPasswordPage extends BasePage {
    private final By heading = By.xpath("//h2[normalize-space()='Восстановление пароля']");
    private final By login = By.linkText("Войти");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public ForgotPasswordPage open() {
        driver.get(baseUrl + "/forgot-password");
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    public LoginPage goToLogin() {
        click(login);
        return new LoginPage(driver).waitForPage();
    }
}
