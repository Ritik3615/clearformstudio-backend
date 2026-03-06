package Order_management.service;

import Order_management.admin.UserRepository;
import Order_management.user.User;
import Order_management.user.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo; // Use the one pointing to the 'users' table
    private final UserRepository adminRepo;

    public CustomUserDetailsService(UserRepo userRepo,UserRepository adminRepo) {
        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We use .orElseThrow so the exception is triggered if the query returns nothing
        Optional<User> userOpt  = userRepo.findByUserName(username);
        if (userOpt.isPresent()){
            User user = userOpt.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .authorities("ROLE_USER")
                    .build();
        }

        // 2. Fallback to Admin repository (UserEntity)
        return adminRepo.findByUsername(username)
                .map(admin -> org.springframework.security.core.userdetails.User.builder()
                        .username(admin.getUsername())
                        .password(admin.getPassword())
                        .authorities("ROLE_ADMIN")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User or Admin not found: " + username));
    }

}

