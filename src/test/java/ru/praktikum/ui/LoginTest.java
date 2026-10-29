package ru.praktikum.ui;

import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.ui.pages.ForgotPasswordPage;
import ru.praktikum.ui.pages.HomePage;
import ru.praktikum.ui.pages.LoginPage;
import ru.praktikum.ui.pages.RegisterPage;
import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseUiTest {
    public LoginTest(String browser) {
        super(browser);
    }

    @Before
    @Step("Создать пользователя перед проверкой входа")
    public void createLoginUser() {
        createAccount();
    }

    @Step("Войти тестовым пользователем и проверить успешный вход")
    private void assertLogin(LoginPage page) {
        assertTrue(page.login(account.email, account.password).isLoggedIn());
    }

    @Test
    public void loginFromHomeButton() {
        assertLogin(new HomePage(driver).open().loginFromHomeButton());
    }

    @Test
    public void loginFromPersonalAccount() {
        assertLogin(new HomePage(driver).open().loginFromPersonalAccount());
    }

    @Test
    public void loginFromRegistrationForm() {
        assertLogin(new RegisterPage(driver).open().goToLogin());
    }

    @Test
    public void loginFromPasswordRecoveryForm() {
        assertLogin(new ForgotPasswordPage(driver).open().goToLogin());
    }
}
