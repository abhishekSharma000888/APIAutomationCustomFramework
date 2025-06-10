package pojos.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookingDates {

    private String checkin;
    private String checkout;
}
