package Order_management.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsByUserName(String username);
    Optional<User> findByUserName(String username);
}
