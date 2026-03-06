package Order_management.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class AdminSignUpRequest {
    private String userName;
    private String password;
    private String name;
    private String email;
    private Long phone;
    private String role;
}
