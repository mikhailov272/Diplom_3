package ru.praktikum.ui;

import io.qameta.allure.Description;
import org.junit.Test;
import ru.praktikum.ui.pages.LoginPage;
import ru.praktikum.ui.pages.RegisterPage;
import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseUiTest {
    public RegistrationTest(String browser) {
        super(browser);
    }

    @Test
    @Description("Регистрация с паролем на допустимой границе: 6 символов")
    public void registrationWithSixCharacterPasswordSucceeds() {
        RegisterPage page = new RegisterPage(driver).open();
        account = new TestAccount();
        page.register(account.name, account.email, account.password);
        assertTrue(new LoginPage(driver).waitForPage().isOpen());
        account.assertRegistrationSaved();
    }

    @Test
    @Description("Пароль из 5 символов вызывает ошибку Некорректный пароль")
    public void fiveCharacterPasswordShowsError() {
        RegisterPage page = new RegisterPage(driver).open();
        account = new TestAccount("Abc12");
        page.register(account.name, account.email, account.password);
        assertTrue(page.passwordErrorIsVisible());
    }
}
