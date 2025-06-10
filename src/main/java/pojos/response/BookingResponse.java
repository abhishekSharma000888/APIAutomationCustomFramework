package pojos.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pojos.request.BookingDates;

@Data
@Getter
@Setter
public class BookingResponse {

    private Integer bookingId;
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private String additionalneeds;
    private BookingDates bookingdates;
}
