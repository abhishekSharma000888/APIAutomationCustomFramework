package crud;

import base.BaseTest;
import endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestHealthCheck extends BaseTest {

    @Test(groups = "reg", priority = 2)
    @TmsLink("https://bugz.atlassian.net/browse/TS-1")
    @Owner("Abhishek")
    @Description("TC#2  - Create Token and Verify")

    public void testHealthCheckPost(){

        //preparing the request
        requestSpecification.basePath(APIConstants.PING_URL);

        //response

        response = RestAssured.given(requestSpecification).when().get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);
    }
}
