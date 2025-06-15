package practicetests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class GetExampleTwo {

    public static void main(String[] args) {

        // Sample token (replace with actual token)
        String token = "123";

        // Send GET request with Authorization header
        Response response = RestAssured.given()
                .baseUri("https://baseUriforTheEndPoint") // Replace with actual base URI
                .basePath("/search")                      // Replace with actual path
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get();

        // Print the full response
        System.out.println("Response Body:\n" + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());

        // ---- ASSERTIONS ----

        // 1. Assert status code
        assertEquals("Expected HTTP 200 response", 200, response.statusCode());

        // 2. Optional: Assert a header if it's expected in response (example only)
        // String headerValue = response.getHeader("Content-Type");
        // assertEquals("Expected Content-Type header", "application/json; charset=utf-8", headerValue);

        // 3. Assert specific fields from JSON response body
        String firstname = response.jsonPath().getString("firstname");
        String lastname = response.jsonPath().getString("lastname");
        int totalPrice = response.jsonPath().getInt("totalprice");
        boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        String checkin = response.jsonPath().getString("bookingdates.checkin");
        String checkout = response.jsonPath().getString("bookingdates.checkout");
        String additionalNeeds = response.jsonPath().getString("additionalneeds");

        // Validate values
        assertEquals("Firstname should be 'test'", "test", firstname);
        assertEquals("Lastname should be 'Brown'", "Brown", lastname);
        assertEquals("Total price should be 111", 111, totalPrice);
        assertTrue("Deposit should be paid", depositPaid);
        assertEquals("Check-in date should match", "2018-01-01", checkin);
        assertEquals("Check-out date should match", "2019-01-01", checkout);
        assertEquals("Additional needs should be 'Breakfast'", "Breakfast", additionalNeeds);
    }
}
