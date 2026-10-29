package ru.praktikum.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By heading = By.xpath("//h2[normalize-space()='Вход']");
    private final By email = By.xpath("//label[normalize-space()='Email']/following-sibling::input");
    private final By password = By.cssSelector("input[type='password']");
    private final By submit = By.xpath("//button[normalize-space()='Войти']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Дождаться страницы входа")
    public LoginPage waitForPage() {
        wait.until(ExpectedConditions.urlContains("/login"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    @Step("Войти с указанными данными")
    public HomePage login(String userEmail, String userPassword) {
        fill(email, userEmail);
        fill(password, userPassword);
        click(submit);
        HomePage home = new HomePage(driver);
        home.isLoggedIn();
        return home;
    }

    @Step("Проверить отображение страницы входа")
    public boolean isOpen() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).isDisplayed();
    }
}
