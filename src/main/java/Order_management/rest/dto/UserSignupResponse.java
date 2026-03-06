package Order_management.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSignupResponse {
    private String userName;
    private String message;

    public UserSignupResponse() {

    }
}
