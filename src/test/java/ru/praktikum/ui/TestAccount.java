package ru.praktikum.ui;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
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

    public void create() {
        Map<String, String> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("name", name);
        Response response = request().body(user).post("/api/auth/register");
        token = response.jsonPath().getString("accessToken");
        response.then().statusCode(200).body("success", equalTo(true));
    }

    public void assertRegistrationSaved() {
        Response response = request().body(Map.of("email", email, "password", password))
                .post("/api/auth/login");
        token = response.jsonPath().getString("accessToken");
        response.then().statusCode(200).body("success", equalTo(true))
                .body("user.email", equalTo(email));
    }

    public void deleteIfCreated() {
        if (token == null) {
            // UI registration may have succeeded before a later assertion failed.
            Response response = request().body(Map.of("email", email, "password", password))
                    .post("/api/auth/login");
            if (response.statusCode() == 401) {
                return;
            }
            token = response.jsonPath().getString("accessToken");
            response.then().statusCode(200).body("success", equalTo(true));
        }
        if (token == null) {
            throw new AssertionError("Нет токена для удаления созданного тестового пользователя");
        }
        request().header("Authorization", token).delete("/api/auth/user")
                .then().statusCode(202).body("success", equalTo(true));
    }
}
