package Order_management.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class DeleteResponse{
    private String username;
    private String name;
    private String email;
    private String message;
}
