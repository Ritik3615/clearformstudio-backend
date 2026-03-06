package Order_management.service;

import Order_management.admin.Role;
import Order_management.admin.RoleRepository;
import Order_management.admin.UserEntity;
import Order_management.admin.UserRepository;
import Order_management.rest.dto.*;
import Order_management.user.User;
import Order_management.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserRepo repo;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public AdminSignUpResponse adminSignUp(AdminSignUpRequest sign) {

        // 1. Validate uniqueness
        if (repository.existsByUsername(sign.getUserName())) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (repository.existsByEmail(sign.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (repository.existsByPhone(sign.getPhone())) {
            throw new IllegalArgumentException("Phone number already registered");
        }

        // 2. Map request → entity
        UserEntity user = new UserEntity();
        user.setName(sign.getName());
        user.setEmail(sign.getEmail());
        user.setUsername(sign.getUserName());
        user.setPhone(sign.getPhone());
        user.setRole(sign.getRole());
        user.setPassword(passwordEncoder.encode(sign.getPassword())); // NOT static

        // 3. Persist
        repository.save(user);

        // 4. Return response DTO
        return new AdminSignUpResponse(
                "Hi "+user.getName()+" your account is created successfully with username of "+user.getUsername()+" you can login now"
        );
    }

    public UserSignupResponse signup(UserSignupRequest request){
        if (repo.existsByUserName(request.getUserName())){
            throw new RuntimeException("User already exists with username Use another");
        }

        User user = new User();
        if (request.getUserName() != null) user.setUserName(request.getUserName());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));

//        Role defaultRole = roleRepository.findByName("ROLE_USER")
//                .orElseThrow(() -> new RuntimeException("Default Role not found"));
//
//        // 2. Assign it to the user
//        user.get().add(defaultRole);

        repo.save(user);

        return new UserSignupResponse(
                user.getUserName(),
                "User created successFully Login now"
        );
    }

    public String login(SignInRequest request) {

        // 1. MANUAL CHECK: If user not in DB, throw the error IMMEDIATELY
        boolean existsAsUser  = repo.existsByUserName(request.getUserName());
        boolean existsAsAdmin  = repository.existsByUsername(request.getUserName());
        // This stops the flow before it even reaches the AuthenticationManager
        if (!existsAsUser  && !existsAsAdmin) {
            throw new RuntimeException("User does not exist. Please signup first.");
        }



        // 2. If user exists, now check the password
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()
                    )
            );

            // 3. If password is correct, generate token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtil.generateToken(userDetails);

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            // This is for when the User exists but the Password is WRONG
            throw new RuntimeException("Invalid Password!");
        }
    }

}
