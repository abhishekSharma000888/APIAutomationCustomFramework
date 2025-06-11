package modules;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import pojos.TokenResponse;
import pojos.request.Auth;
import pojos.request.Booking;
import pojos.request.BookingDates;
import pojos.response.BookingResponse;

import java.awt.print.Book;

public class PayloadManager {

    Gson gson = new Gson();
    Faker faker = new Faker();

    // Convert the Java Object into the JSON String to use it as the payload.
    // Serialization
    public String createPayloadBookingAsString() {

        Booking booking = new Booking();
        booking.setFirstname("Abhishek");
        booking.setLastname("Sharma");
        booking.setTotalprice(100);
        booking.setDepositpaid(true);

        BookingDates bookingDates = new BookingDates();

        bookingDates.setCheckin("2018-01-01");
        bookingDates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");


        System.out.println(booking);
        // we need to convert the data into JSON

        Gson gson = new Gson();

        String jsonStringBooking = gson.toJson(booking);

        return jsonStringBooking;

    }
    // Convert JSON back to Java Object and the process used is DeSerialization

    public BookingResponse bookingResponseJava(String responseString) {

        Gson gson = new Gson();

        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);

        return bookingResponse;
    }

    // Java Object -> JSON
    public String setAuthPayload() {
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        System.out.println("Payload set to the -> " + jsonPayloadString);
        return jsonPayloadString;

    }

    // DeSerealization ( JSON String -> Java Object
    public String getTokenFromJSON(String tokenResponse) {
        gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
        return tokenResponse1.getToken();
    }

    public String createPayloadBookingAsStringWrongBody() {
        Booking booking = new Booking();
        booking.setFirstname("会意; 會意");
        booking.setLastname("会意; 會意");
        booking.setTotalprice(112);
        booking.setDepositpaid(false);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("5025-02-01");
        bookingdates.setCheckout("5025-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("会意; 會意");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

    }

    public Booking getResponseFromJSON(String getResponse) {
        gson = new Gson();
        Booking booking = gson.fromJson(getResponse, Booking.class);
        return booking;
    }

    public String createPayloadBookingFakerJS() {
        faker = new Faker();
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(1, 1000));
        booking.setDepositpaid(faker.random().nextBoolean());

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

    }

    public Booking getResponseFromTheJSON(String getResponse) {
        gson = new Gson();
        Booking booking = gson.fromJson(getResponse, Booking.class);
        return booking;
    }

}
