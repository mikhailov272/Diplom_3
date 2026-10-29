package ru.praktikum.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private final By heading = By.xpath("//h1[normalize-space()='Соберите бургер']");
    private final By loginButton = By.xpath("//button[normalize-space()='Войти в аккаунт']");
    private final By accountLink = By.xpath("//a[.//p[translate(normalize-space(.), 'ЛК', 'лк')='личный кабинет']]");
    private final By orderButton = By.xpath("//button[normalize-space()='Оформить заказ']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public HomePage open() {
        driver.get(baseUrl + "/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return this;
    }

    @Step("Перейти ко входу по кнопке главной страницы")
    public LoginPage loginFromHomeButton() {
        click(loginButton);
        return new LoginPage(driver).waitForPage();
    }

    @Step("Перейти ко входу через личный кабинет")
    public LoginPage loginFromPersonalAccount() {
        click(accountLink);
        return new LoginPage(driver).waitForPage();
    }

    @Step("Проверить отображение кнопки оформления заказа")
    public boolean isLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton)).isDisplayed();
    }

    @Step("Выбрать раздел конструктора: {category}")
    public void selectCategory(String category) {
        click(categoryTab(category));
    }

    @Step("Проверить выбранный раздел конструктора: {category}")
    public boolean categoryIsSelected(String category) {
        return wait.until(ignored -> {
            WebElement tab = driver.findElement(categoryTab(category));
            return tab.getAttribute("class").contains("tab_type_current");
        });
    }

    private By categoryTab(String category) {
        return By.xpath("//span[normalize-space()='" + category + "']/parent::div");
    }
}
