package e2e_integration;

import base.BaseTest;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import request.Booking;
import response.BookingResponse;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestIntegrationFlow1 extends BaseTest {

    @Test(groups = "qa", priority = 1)
    @Owner("Abhishek")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
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

        System.out.println("Stored booking ID: " + bookingId);
    }

    @Test(groups = "qa", priority = 2, dependsOnMethods = "testCreateBooking")
    @Owner("Abhishek")
    @Description("TC#INT1 - Step 2. Verify that the Booking can be Retrieved by ID")
    public void testVerifyBookingId(ITestContext iTestContext) {
        Integer bookingId = getBookingIdFromContext(iTestContext);
        assertThat(bookingId)
                .withFailMessage("Booking ID was not properly set in test context")
                .isNotNull();

        String endpoint = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId;
        System.out.println("Attempting to GET booking at: " + endpoint);

        requestSpecification.basePath(endpoint);
        response = RestAssured.given(requestSpecification)
                .when()
                .get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
    }

    @Test(groups = "qa", priority = 3, dependsOnMethods = "testVerifyBookingId")
    @Owner("Abhishek")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        Integer bookingId = getBookingIdFromContext(iTestContext);
        assertThat(bookingId)
                .withFailMessage("Booking ID not found for update")
                .isNotNull();

        String token = getToken();
        iTestContext.setAttribute("token", token);

        String endpoint = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId;
        System.out.println("Updating booking at: " + endpoint);

        requestSpecification.basePath(endpoint);

        response = RestAssured.given(requestSpecification)
                .cookie("token", token)
                .when()
                .body(payloadManager.fullUpdatePayloadAsString())
                .put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        Booking updatedBooking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(updatedBooking.getFirstname());
        assertActions.verifyStringKey(updatedBooking.getFirstname(), "Lucky");
    }

    @Test(groups = "qa", priority = 4, dependsOnMethods = "testUpdateBookingByID")
    @Owner("Abhishek")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
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

        validatableResponse.statusCode(201);
    }

    private Integer getBookingIdFromContext(ITestContext context) {
        Integer bookingId = (Integer) context.getAttribute("bookingid");
        if (bookingId == null) {
            bookingId = (Integer) context.getSuite().getAttribute("bookingid");
        }
        return bookingId;
    }
}
