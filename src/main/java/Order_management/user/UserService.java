//package Order_management.user;
//
//import Order_management.rest.dto.UserSignupRequest;
//import Order_management.rest.dto.UserSignupResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@AllArgsConstructor
//@Service
//public class UserService {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepo repo;
//
//    public UserSignupResponse signup(UserSignupRequest userSignupRequest){
//        if (repo.findByUserName(userSignupRequest.getUserName())){
//            throw new RuntimeException("User already exists with username");
//        }
//
//        User user = new User();
//        user.setUserName(userSignupRequest.getUserName());
//        user.setUserName(passwordEncoder.encode(userSignupRequest.getPassword()));
//        repo.save(user);
//
//        return new UserSignupResponse(
//                user.getUserName(),
//                "User created successfully"
//        );
//    }
//}
