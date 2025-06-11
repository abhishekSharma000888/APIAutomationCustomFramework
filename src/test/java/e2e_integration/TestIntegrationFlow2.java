package e2e_integration;

import base.BaseTest;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import pojos.response.BookingResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow2 extends BaseTest {

    // Flow: Create Booking -> Delete Booking -> Verify Deletion

    @Test(groups = "qa", priority = 1)
    @Owner("Abhishek")
    @Description("TC#INT2 - Step 1. Create a Booking Successfully")
    public void testCreateBooking(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when()
                .body(payloadManager.createPayloadBookingAsString())
                .post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Abhishek");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        Integer bookingId = bookingResponse.getBookingid();
        iTestContext.setAttribute("bookingid", bookingId);
        iTestContext.getSuite().setAttribute("bookingid", bookingId);

        String token = getToken();
        iTestContext.setAttribute("token", token);

        System.out.println("Stored booking ID: " + bookingId);
    }

    @Test(groups = "qa", priority = 2, dependsOnMethods = "testCreateBooking")
    @Owner("Abhishek")
    @Description("TC#INT2 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {
        Integer bookingId = getBookingIdFromContext(iTestContext);
        String token = (String) iTestContext.getAttribute("token");

        assertThat(bookingId).withFailMessage("Booking ID not available for delete").isNotNull();
        assertThat(token).withFailMessage("Token not available for delete").isNotNull();

        String endpoint = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId;
        System.out.println("Deleting booking at: " + endpoint);

        requestSpecification.basePath(endpoint).cookie("token", token);

        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when()
                .delete()
                .then()
                .log().all();

        validatableResponse.statusCode(201); // 201 is specific to your service's DELETE response
    }

    @Test(groups = "qa", priority = 3, dependsOnMethods = "testDeleteBookingById")
    @Owner("Abhishek")
    @Description("TC#INT2 - Step 3. Verify that the Booking is Deleted")
    public void testVerifyBookingDeleted(ITestContext iTestContext) {
        Integer bookingId = getBookingIdFromContext(iTestContext);
        assertThat(bookingId).withFailMessage("Booking ID not available for verification").isNotNull();

        String endpoint = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId;
        requestSpecification.basePath(endpoint);

        response = RestAssured.given().spec(requestSpecification)
                .when()
                .get();

        response.then().log().all();

        // Assuming 404 means "Not Found" after deletion
        assertThat(response.getStatusCode()).withFailMessage("Expected 404 after deletion").isEqualTo(404);
    }

    private Integer getBookingIdFromContext(ITestContext context) {
        Integer bookingId = (Integer) context.getAttribute("bookingid");
        if (bookingId == null) {
            bookingId = (Integer) context.getSuite().getAttribute("bookingid");
        }
        return bookingId;
    }
}
