package pojos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pojos.request.Booking;
import pojos.request.BookingDates;

@Data
@Getter
@Setter
public class BookingResponse {

    private Integer bookingid;
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private String additionalneeds;
    private BookingDates bookingdates;

    private Booking booking; // This will contain all the nested booking details

}
