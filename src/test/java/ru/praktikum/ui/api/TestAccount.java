package ru.praktikum.ui.api;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.ui.model.LoginRequest;
import ru.praktikum.ui.model.UserRequest;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;

public class TestAccount {
    public final String email = "qa-ui-" + UUID.randomUUID() + "@example.com";
    public final String password;
    public final String name = "Test User";
    private String token;

    public TestAccount() {
        this("Abc123");
    }

    public TestAccount(String password) {
        this.password = password;
    }

    private RequestSpecification request() {
        return given().baseUri(System.getProperty("base.url", "https://qa-stellarburgers.education-services.ru"))
                .contentType(ContentType.JSON)
                .config(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000)));
    }

    @Step("Создать тестового пользователя через API")
    public void create() {
        UserRequest user = new UserRequest(email, password, name);
        Response response = request().body(user).post("/api/auth/register");
        token = response.jsonPath().getString("accessToken");
        response.then().statusCode(SC_OK).body("success", equalTo(true));
    }

    @Step("Проверить сохранение регистрации через вход по API")
    public void assertRegistrationSaved() {
        Response response = request().body(new LoginRequest(email, password))
                .post("/api/auth/login");
        token = response.jsonPath().getString("accessToken");
        response.then().statusCode(SC_OK).body("success", equalTo(true))
                .body("user.email", equalTo(email));
    }

    @Step("Удалить созданного тестового пользователя")
    public void deleteIfCreated() {
        if (token == null) {
            // UI registration may have succeeded before a later assertion failed.
            Response response = request().body(new LoginRequest(email, password))
                    .post("/api/auth/login");
            if (response.statusCode() == SC_UNAUTHORIZED) {
                return;
            }
            token = response.jsonPath().getString("accessToken");
            response.then().statusCode(SC_OK).body("success", equalTo(true));
        }
        if (token == null) {
            throw new AssertionError("Нет токена для удаления созданного тестового пользователя");
        }
        request().header("Authorization", token).delete("/api/auth/user")
                .then().statusCode(SC_ACCEPTED).body("success", equalTo(true));
    }
}
