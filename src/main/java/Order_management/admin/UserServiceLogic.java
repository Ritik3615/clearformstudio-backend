package Order_management.admin;

import Order_management.admin.UserEntity;
import Order_management.rest.dto.AdminSignUpRequest;
import Order_management.rest.dto.AdminSignUpResponse;
import Order_management.rest.dto.DeleteResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceLogic {
    private final UserRepository repository;

    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User with username " + username + " not found"
                        )
                );
    }

    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not exists with this email: " + email
                        )
                );
    }

    public boolean hasUserWithUsername(String username) {
        return repository.existsByUsername(username);
    }

    public AdminSignUpResponse update(String email, AdminSignUpRequest sign) {

        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not available with email: " + email)
                );

        if (sign.getName() != null) user.setName(sign.getName());
        if (sign.getPhone() != null) user.setPhone(sign.getPhone());

        repository.save(user);

        return new AdminSignUpResponse(
//                user.getUsername(),
//                user.getName(),
//                user.getEmail(),
                "User updated successfully"
        );
    }

    public DeleteResponse delete(Long id) {

        UserEntity user = repository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User does not exist with id: " + id)
                );

        DeleteResponse response = new DeleteResponse(
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                "Deleted successfully"
        );

        repository.delete(user);
        return response;
    }
}
