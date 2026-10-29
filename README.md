# Diplom_3: UI Stellar Burgers

Java 11, Maven, JUnit 4, Selenium, Page Object, Allure.

```sh
mvn clean test
mvn allure:report
```

По умолчанию каждый сценарий запускается в Google Chrome и Яндекс Браузере.
Для одного браузера: `-Dbrowser=chrome` или `-Dbrowser=yandex`.
Тесты запускают отдельный браузерный профиль, пользовательский профиль не используется.

Параметры:

- `base.url`: адрес сервиса, по умолчанию альтернативный стенд `https://qa-stellarburgers.education-services.ru`;
- `yandex.binary`: путь к установленному Яндекс Браузеру (по умолчанию путь macOS);
- `chrome.binary`: необязательный путь к Chrome;
- `chrome.driver`, `yandex.driver`: необязательные пути к совместимому ChromeDriver;
- `headless=true`: необязательный запуск без видимого окна;
- `ui.timeout.seconds`: время ожидания элементов, по умолчанию 15.

ChromeDriver для Яндекс Браузера должен соответствовать версии его Chromium.
Без явно указанного драйвера используется Selenium Manager. Он может неверно
определить версию Яндекс Браузера; в этом случае укажите `yandex.driver` явно.
Версия движка доступна на странице `browser://version`.

Пример запуска обоих браузеров с драйвером для Яндекс Браузера:

```sh
mvn clean test -Dheadless=true -Dyandex.driver=/path/to/compatible/chromedriver
```

Драйвер загружается из официального каталога
[Chrome for Testing](https://googlechromelabs.github.io/chrome-for-testing/).

Область проверок: регистрация с паролем из 6 символов и ошибка с паролем из 5,
четыре пути входа, переходы «Булки», «Соусы», «Начинки». Подготовка и удаление
тестовых пользователей выполняются через API. На ошибке прикладывается скриншот.
В проверках входа пользователь создаётся в `@Before`, удаляется в `@After`.
В проверках регистрации создание пользователя через форму является действием теста.
Тела API-запросов сериализуются из моделей `UserRequest` и `LoginRequest`.
Методы шагов отмечены `@Step`; для их записи в Allure подключён AspectJ.
Модели используют Lombok `@Data` и `@AllArgsConstructor`. Класс `TestAccount`
расположен в пакете `ru.praktikum.ui.api`; HTTP-коды заданы константами `HttpStatus`.

Локаторы полей регистрации и входа проверены на альтернативном стенде.
В Allure для каждого запуска сохраняются название браузера, фактическая версия
движка из WebDriver и признак запуска без видимого окна.

## Результат прогона 29.10.2026

18 PASSED, 0 FAILED, 0 BROKEN, 0 SKIPPED: по 9 сценариев в каждом браузере.
Проверены регистрация, четыре пути входа и три раздела конструктора.
Созданные тестовые пользователи удалены, ошибок уборки нет.

Окружение: macOS 27.0.1, Java 11.0.31; запуск без видимого окна (`headless=true`).

- Google Chrome 156.0.8078.48, ChromeDriver 156.0.8078.47.
- Яндекс Браузер 26.8.3.1016, Chromium 150.0.7871.1016,
  ChromeDriver 150.0.7871.124.

Исходные данные отчёта: `target/allure-results`. После `mvn allure:report`
HTML-отчёт находится в `target/site/allure-maven-plugin/index.html`.
Для просмотра через локальный сервер: `mvn allure:serve`.

## Сохранённый отчёт

В `allure-results/` сохранены результаты прогона 29.10.2026 — все 18 запусков
в Google Chrome и Яндекс Браузере. Для просмотра без повторного запуска тестов
выполните из корня проекта:

```sh
mvn allure:serve -Dallure.results.directory="${PWD}/allure-results"
```

Чтобы только сформировать HTML, замените `allure:serve` на `allure:report`.
