package ru.praktikum.ui;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public final class BrowserFactory {
    private BrowserFactory() { }

    public static WebDriver create(String browser) {
        ChromeOptions options = new ChromeOptions();
        if ("yandex".equals(browser)) {
            options.setBinary(System.getProperty("yandex.binary", "/Applications/Yandex.app/Contents/MacOS/Yandex"));
        } else if ("chrome".equals(browser)) {
            String binary = System.getProperty("chrome.binary");
            if (binary != null) {
                options.setBinary(binary);
            }
        } else {
            throw new IllegalArgumentException("Поддерживаются только chrome и yandex: " + browser);
        }
        options.addArguments("--window-size=1280,900");
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        String driverPath = System.getProperty(browser + ".driver");
        WebDriver driver = driverPath == null ? new ChromeDriver(options)
                : new ChromeDriver(new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(driverPath)).build(), options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }
}
