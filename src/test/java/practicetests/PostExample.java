package practicetests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostExample {

    public static void main(String[] args){

        String requestBody= "{\n" +
                "    \"firstname\" : \"test\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";


        Response response = RestAssured.given()
                .baseUri("https://example")
                .basePath("books")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post();

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response body:" + response.getBody().asString());

    }
}
