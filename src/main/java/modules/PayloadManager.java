package modules;

import com.google.gson.Gson;
import pojos.request.Booking;
import pojos.request.BookingDates;
import pojos.response.BookingResponse;

import java.awt.print.Book;

public class PayloadManager {

     // Convert the Java Object into the JSON String to use it as the payload.
    // Serialization
    public String createPayloacBookingAsString(){

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

    public BookingResponse bookingResponseJava(String responseString){

        Gson gson = new Gson();

        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);

            return bookingResponse;
    }

}
