package practicetests;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class GetExample {

    public static void main(String[] args) {


        // Perform GET request and validate response
        Response response = given()
                .baseUri("https://example.com")
                .basePath("/books/{name}") // using pathParam
                .pathParam("name", "Mega")
                .queryParam("page", 2)
                .contentType(ContentType.JSON)
                // ✅ Basic Auth
                .auth().preemptive().basic("yourUsername", "yourPassword")
                // ✅ Bearer Token
                .header("Authorization", "Bearer YOUR_ACCESS_TOKEN")
                // ✅ Custom Token Header (if needed)
                .header("X-Auth-Token", "abc123token")
                //uploading files
                .multiPart("file", new File("location"))
                .filter(new RequestLoggingFilter())
                .log().all()
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(200)
                //Schema Validator
                .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .extract().response();

        // Print status code and response body
        System.out.println("Getting status code: " + response.statusCode());
        System.out.println("Getting the response: " + response.getBody().asString());

        String token = response.jsonPath().getString("token");

        System.out.println("Extracted Toke:"+ token);
    }
}
