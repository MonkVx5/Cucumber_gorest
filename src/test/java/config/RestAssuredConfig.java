package config;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RestAssuredConfig {
    private static String TOKEN = "c269cfd18cd6a66306ecb8822ce1d8be008efe53e6cb54ec9fc9f4073567314a";

    static {
        RestAssured.baseURI = "https://gorest.co.in/public/v2";
    }

    public static RequestSpecification getRequestSpecification() {
        return RestAssured.given()
            .header("Authorization", "Bearer " + TOKEN)
            .header("Content-Type", "application/json");
    }
}
