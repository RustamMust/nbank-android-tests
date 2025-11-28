package helpers;

import static io.qameta.allure.Allure.step;

import org.apache.http.HttpStatus;

import config.Config;
import io.restassured.RestAssured;

public class ApiHelper {
    public static void healthCheck() {
        step("Check API health before start testing", () -> {
            RestAssured.given()
                    .baseUri(Config.getProperty("backend.url"))
                    .header("accept", "*/*")
                    .header("Authorization", "Basic " + Config.getProperty("admin.token"))
                    .when()
                    .get("/api/v1/admin/users")
                    .then()
                    .assertThat().statusCode(HttpStatus.SC_OK);
        });
    }
}
