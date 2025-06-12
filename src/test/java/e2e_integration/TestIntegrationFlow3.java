package e2e_integration;

import base.BaseTest;
import com.google.common.reflect.TypeToken;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import response.BookingResponse;

import java.lang.reflect.Type;
import java.util.List;

public class TestIntegrationFlow3 extends BaseTest {

    //Get all bookings and delete a Booking
    @Test(groups = "qa", priority = 1)
    @Owner("Abhishek")
    @Description("TC#INT1 - Step 1.Get All booking")
    public void testGetAllBooking(ITestContext iTestContext) {

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when()
                .get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Parse response as a list of BookingResponse
        Type listType = new TypeToken<List<BookingResponse>>() {
        }.getType();
        List<BookingResponse> bookingList = payloadManager.gson.fromJson(response.asString(), listType);

        // Pick the first booking ID (or random if you want)
        Integer bookingId = bookingList.get(0).getBookingid();

        // Assert and store
        assertActions.verifyStringKeyNotNull(String.valueOf(bookingId));
        iTestContext.setAttribute("bookingid", bookingId);
        iTestContext.getSuite().setAttribute("bookingid", bookingId);

        System.out.println("Stored booking ID: " + bookingId);
    }

    @Test(groups = "qa", priority = 2, dependsOnMethods = "testGetAllBooking")
    @Owner("Abhishek")
    @Description("TC#INT2 - Step 2. Delete the selected booking and validate deletion")
    public void testDeleteBooking(ITestContext iTestContext) {
        Integer bookingId = (Integer) iTestContext.getAttribute("bookingid");

        // Rebuild request spec with auth token
        String token = getToken();

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId)
                .header("Cookie", "token=" + token);

        response = RestAssured.given(requestSpecification)
                .when()
                .delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201); // 201 = Created (used by Restful Booker API for successful delete)

        // Validate deletion by trying to GET the same booking (expect 404)
        response = RestAssured.given()
                .baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingId)
                .when()
                .get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);
    }

}