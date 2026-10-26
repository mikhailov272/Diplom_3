package ru.praktikum.ui;

import org.junit.Test;
import ru.praktikum.ui.pages.HomePage;
import static org.junit.Assert.assertTrue;

public class ConstructorTest extends BaseUiTest {
    public ConstructorTest(String browser) {
        super(browser);
    }

    @Test
    public void switchToBunsFromAnotherCategory() {
        HomePage page = new HomePage(driver).open();
        page.selectCategory("Соусы");
        assertTrue(page.categoryIsSelected("Соусы"));
        page.selectCategory("Булки");
        assertTrue(page.categoryIsSelected("Булки"));
    }

    @Test
    public void switchToSauces() {
        HomePage page = new HomePage(driver).open();
        page.selectCategory("Соусы");
        assertTrue(page.categoryIsSelected("Соусы"));
    }

    @Test
    public void switchToFillings() {
        HomePage page = new HomePage(driver).open();
        page.selectCategory("Начинки");
        assertTrue(page.categoryIsSelected("Начинки"));
    }
}
