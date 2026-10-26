package ru.praktikum.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {
    private final By heading = By.xpath("//h2[normalize-space()='Регистрация']");
    private final By name = By.xpath("//label[normalize-space()='Имя']/following-sibling::input");
    private final By email = By.xpath("//label[normalize-space()='Email']/following-sibling::input");
    private final By password = By.cssSelector("input[type='password']");
    private final By register = By.xpath("//button[normalize-space()='Зарегистрироваться']");
    private final By login = By.linkText("Войти");
    private final By passwordError = By.xpath("//*[normalize-space()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        driver.get(baseUrl + "/register");
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    public void register(String userName, String userEmail, String userPassword) {
        fill(name, userName);
        fill(email, userEmail);
        fill(password, userPassword);
        click(register);
    }

    public boolean passwordErrorIsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).isDisplayed();
    }

    public LoginPage goToLogin() {
        click(login);
        return new LoginPage(driver).waitForPage();
    }
}
