package gourp77.user;

import gourp77.exceptions.InvalidSecurityAnswerException;
import gourp77.user.service.IUserService;
import gourp77.user.userDetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDetails userDetails) {
        try {
            return new ResponseEntity<>(userService.createUser(userDetails), HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDetails userDetails) {
        try {
            return new ResponseEntity<>(userService.loginUser(userDetails), HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> userInfo(@RequestParam String token) {
        try {
            return new ResponseEntity<>(userService.getUserInfo(token), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/security-question")
    public ResponseEntity<String> getSecurityQuestion(@RequestParam String email) {
        try {
            return new ResponseEntity<>(userService.getSecurityQuestion(email), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserDetails userDetails) {
        try {
            return new ResponseEntity<>(userService.resetPassword(userDetails), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | InvalidSecurityAnswerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
