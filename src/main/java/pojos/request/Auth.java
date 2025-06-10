package pojos.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Auth {

    private String username;
    private String password;

}
