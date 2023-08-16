package gourp77.user;

import gourp77.exceptions.InvalidSecurityAnswerException;
import gourp77.user.service.IUserService;
import gourp77.user.userDetails.UserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private User user;

    @Test
    void registerUser_Valid() {
        when(userService.createUser(any(UserDetails.class))).thenReturn("User created successfully");

        ResponseEntity<String> expect = new ResponseEntity<>("User created successfully", HttpStatus.OK);
        ResponseEntity<String> result = userController.registerUser(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void registerUser_IllegalArgumentException() {
        when(userService.createUser(any(UserDetails.class)))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.registerUser(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void registerUser_IllegalStateException() {
        when(userService.createUser(any(UserDetails.class)))
                .thenThrow(new IllegalStateException("IllegalStateException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalStateException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.registerUser(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void loginUser_Valid() {
        when(userService.loginUser(any(UserDetails.class))).thenReturn("User logged in successfully");

        ResponseEntity<String> expect = new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
        ResponseEntity<String> result = userController.loginUser(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void loginUser_IllegalArgumentException() {
        when(userService.loginUser(any(UserDetails.class)))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.loginUser(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void loginUser_IllegalStateException() {
        when(userService.loginUser(any(UserDetails.class)))
                .thenThrow(new IllegalStateException("IllegalStateException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalStateException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.loginUser(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void userInfo_Valid() throws CredentialNotFoundException, CredentialExpiredException {
        when(userService.getUserInfo(anyString())).thenReturn(user);

        ResponseEntity<?> expect = new ResponseEntity<>(user, HttpStatus.OK);
        ResponseEntity<?> result = userController.userInfo("Token");

        assertEquals(expect, result);
    }

    @Test
    void userInfo_IllegalArgumentException() throws CredentialNotFoundException, CredentialExpiredException {
        when(userService.getUserInfo(anyString())).thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = userController.userInfo("Token");

        assertEquals(expect, result);
    }

    @Test
    void userInfo_CredentialNotFoundException() throws CredentialNotFoundException, CredentialExpiredException {
        when(userService.getUserInfo(anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = userController.userInfo("Token");

        assertEquals(expect, result);
    }

    @Test
    void userInfo_CredentialExpiredException() throws CredentialNotFoundException, CredentialExpiredException {
        when(userService.getUserInfo(anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = userController.userInfo("Token");

        assertEquals(expect, result);
    }

    @Test
    void getSecurityQuestion_Valid() throws CredentialNotFoundException {
        when(userService.getSecurityQuestion(anyString())).thenReturn("Security question");

        ResponseEntity<String> expect = new ResponseEntity<>("Security question", HttpStatus.OK);
        ResponseEntity<String> result = userController.getSecurityQuestion("Email");

        assertEquals(expect, result);
    }

    @Test
    void getSecurityQuestion_IllegalArgumentException() throws CredentialNotFoundException {
        when(userService.getSecurityQuestion(anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.getSecurityQuestion("Email");

        assertEquals(expect, result);
    }

    @Test
    void getSecurityQuestion_CredentialNotFoundException() throws CredentialNotFoundException {
        when(userService.getSecurityQuestion(anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.getSecurityQuestion("Email");

        assertEquals(expect, result);
    }

    @Test
    void resetPassword_Valid() throws CredentialNotFoundException, InvalidSecurityAnswerException {
        when(userService.resetPassword(any(UserDetails.class))).thenReturn("Password reset successful");

        ResponseEntity<String> expect = new ResponseEntity<>("Password reset successful", HttpStatus.OK);
        ResponseEntity<String> result = userController.resetPassword(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void resetPassword_IllegalArgumentException() throws CredentialNotFoundException, InvalidSecurityAnswerException {
        when(userService.resetPassword(any(UserDetails.class)))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.resetPassword(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void resetPassword_CredentialNotFoundException() throws CredentialNotFoundException,
            InvalidSecurityAnswerException {
        when(userService.resetPassword(any(UserDetails.class)))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.resetPassword(new UserDetails());

        assertEquals(expect, result);
    }

    @Test
    void resetPassword_InvalidSecurityAnswerException() throws CredentialNotFoundException,
            InvalidSecurityAnswerException {
        when(userService.resetPassword(any(UserDetails.class)))
                .thenThrow(new InvalidSecurityAnswerException("InvalidSecurityAnswerException"));

        ResponseEntity<String> expect = new ResponseEntity<>("InvalidSecurityAnswerException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = userController.resetPassword(new UserDetails());

        assertEquals(expect, result);
    }
}