package utils;

import io.restassured.RestAssured;
import utils.config;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiSimulator {
    public static void simulateBiometric(String type, boolean success){
        RestAssured.baseURI = config.Config.API_BASE;
        given()
                .contentType("application/json")
                .body("{\"userId\":1,\"authType\":\""+type+"\",\"success\":"+success+"}")
                .when()
                .post("/auth/biometric")
                .then()
                .statusCode(200)
                .body("success", equalTo(success));
    }
}
