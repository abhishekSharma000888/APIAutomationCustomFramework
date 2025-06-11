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


    @Test(groups = "reg", priority = 1)
    @Owner("Abhishek")
    @Description("TC#1 - Verify that the Booking can't be Created, When Payload is null")
    public void testCreateBookingPOST_Negative() {

        System.out.println("I am in the starting of the test case!");
    }







    }
