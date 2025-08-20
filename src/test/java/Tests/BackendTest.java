package Tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BackendTest {
    @Test
        public class BackendTests {

            @Test
            public void simulateBiometricSuccess() {
                RestAssured.baseURI = "https://mockapi.example.com";

                given()
                        .contentType("application/json")
                        .body("{ \"userId\": 1, \"authType\": \"faceid\" }")
                        .when()
                        .post("/auth/biometric")
                        .then()
                        .statusCode(200)
                        .body("success", equalTo(true));
            }
        }

    }

