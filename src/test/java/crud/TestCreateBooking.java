package crud;

import base.BaseTest;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import endpoints.APIConstants;
import pojos.response.BookingResponse;

public class TestCreateBooking extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Abhishek")
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString()).log().all().post();
        System.out.println(response.asString());
        // Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Verification Part
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Abhishek");
    }

    @Test(groups = "reg", priority = 2)
    @Owner("Abhishek")
    @Description("TC#1 - Verify that the Booking cannot be created when the payload is null")
    public void testCreateBookingPOST_Negative() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body("{}").log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(500);
    }

    @Test(groups = "reg", priority = 2)
    @Owner("Abhishek")
    @Description("TC#1 - Verify that the Booking cannot be created when the payload is null")
    public void testCreateBookingPOST_Positive02() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsStringWrongBody())
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
    }

    @Test(groups = "reg", priority = 1)
    @Owner("Abhishek")
    @Description("TC#1 - Verify that the Booking can be Created, When Payload is RANDOM")
    public void testCreateBookingPOST_POSITIVE_RANDOM_DATA() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingFakerJS()).log().all().post();
        System.out.println(response.asString());

        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(200);

    }

    }
