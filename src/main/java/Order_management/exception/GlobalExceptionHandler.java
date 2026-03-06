package Order_management.exception;

import Order_management.rest.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handleRuntimeException(RuntimeException ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage()); // This will be "User does not exist..."
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<Response> handleBadCredentials(org.springframework.security.authentication.BadCredentialsException ex) {
        Response response = new Response();
        response.setMessage("Invalid username or password!");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
