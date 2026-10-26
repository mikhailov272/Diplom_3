package ru.praktikum.ui;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@RunWith(Parameterized.class)
public abstract class BaseUiTest {
    protected final String browser;
    protected WebDriver driver;
    protected TestAccount account;

    protected BaseUiTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "browser={0}")
    public static Collection<Object[]> browsers() {
        return Arrays.stream(System.getProperty("browser", "chrome,yandex").split(","))
                .map(String::trim).map(browser -> new Object[] {browser})
                .collect(Collectors.toList());
    }

    @Rule
    public final TestWatcher evidenceAndShutdown = new TestWatcher() {
        @Override
        protected void failed(Throwable error, Description description) {
            if (driver != null) {
                try {
                    byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Ошибка: " + browser, "image/png", new ByteArrayInputStream(png), "png");
                } catch (RuntimeException screenshotError) {
                    error.addSuppressed(screenshotError);
                }
            }
        }

        @Override
        protected void finished(Description description) {
            if (driver != null) {
                driver.quit();
            }
        }
    };

    @Before
    public void openFreshBrowser() {
        Allure.parameter("Браузер", browser);
        driver = BrowserFactory.create(browser);
        Allure.parameter("Версия движка", ((HasCapabilities) driver).getCapabilities().getBrowserVersion());
        Allure.parameter("Без видимого окна", Boolean.getBoolean("headless"));
    }

    protected TestAccount createAccount() {
        account = new TestAccount();
        account.create();
        return account;
    }

    @After
    public void removeTestAccount() {
        if (account != null) {
            account.deleteIfCreated();
        }
    }
}
