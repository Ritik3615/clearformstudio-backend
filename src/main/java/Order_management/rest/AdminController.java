package Order_management.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {

    @GetMapping("/hello")
    public String secureHello() {
        return "Access Granted! Your JWT is valid.";
    }
}

