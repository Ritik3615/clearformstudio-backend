package Order_management.rest;

import Order_management.rest.dto.*;
import Order_management.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/adminsignup")
    public ResponseEntity<AdminSignUpResponse> createAdmin(@RequestBody AdminSignUpRequest request){
        AdminSignUpResponse response = authService.adminSignUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> signup(@RequestBody UserSignupRequest request){
        UserSignupResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody SignInRequest request){
        String msg = authService.login(request);
        Response response = new Response();
        response.setMessage("Login SuccessFull");
        response.setToken(msg);

        return ResponseEntity.ok(response);
    }

}
