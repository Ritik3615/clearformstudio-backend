package Order_management.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

@Getter
@Setter
@Data
@AllArgsConstructor
public class SignInRequest {
    private String userName;
    private String password;

}
