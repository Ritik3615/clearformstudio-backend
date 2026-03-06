package Order_management.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication") // Matches your permitAll() config
public class PublicController {

    @GetMapping("/test")
    public String hello() {
        return "Hello! The public endpoint is working.";
    }
}

