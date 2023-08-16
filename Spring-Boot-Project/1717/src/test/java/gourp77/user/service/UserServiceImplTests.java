package gourp77.user.service;

import gourp77.exceptions.InvalidSecurityAnswerException;
import gourp77.token.Token;
import gourp77.token.service.ITokenService;
import gourp77.user.User;
import gourp77.user.UserRepository;
import gourp77.user.service.UserServiceImpl;
import gourp77.user.userDetails.UserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.CredentialNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ITokenService tokenService;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserDetails userDetails;

    @Mock
    User user;

    @Mock
    Token token;

    @Test
    void createUserTest_ValidUser() {
        when(userRepository.existsUserByUsername(any())).thenReturn(false);
        when(userRepository.existsUserByEmail(any())).thenReturn(false);

        String result = userService.createUser(userDetails);

        assertEquals(result, "User created successfully");
    }

    @Test
    void createUserTest_UsernameTaken() {
        when(userRepository.existsUserByUsername(any())).thenReturn(true);

        String error = "";
        try {
            userService.createUser(userDetails);
        } catch (IllegalStateException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Username already in use");
    }

    @Test
    void createUserTest_EmailTaken() {
        when(userRepository.existsUserByUsername(any())).thenReturn(false);
        when(userRepository.existsUserByEmail(any())).thenReturn(true);

        String error = "";
        try {
            userService.createUser(userDetails);
        } catch (IllegalStateException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Email already in use");
    }

    @Test
    void loginUserTest_ValidLogin() {
        when(userRepository.existsUserByEmail(any())).thenReturn(true);
        when(userRepository.findUserByEmail(any())).thenReturn(user);
        when(user.getPassword()).thenReturn("Password");
        when(userDetails.getPassword()).thenReturn("Password");
        when(tokenService.generateToken(any())).thenReturn(token);
        when(token.getToken()).thenReturn("Token");

        String result = userService.loginUser(userDetails);

        assertEquals(result, "Token");
    }

    @Test
    void loginUserTest_UserDoesNotExist() {
        when(userRepository.existsUserByEmail(any())).thenReturn(false);

        String error = "";
        try {
            userService.loginUser(userDetails);
        } catch (IllegalStateException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Invalid credentials");
    }

    @Test
    void loginUserTest_IncorrectPassword() {
        when(userRepository.existsUserByEmail(any())).thenReturn(true);
        when(userRepository.findUserByEmail(any())).thenReturn(user);
        when(user.getPassword()).thenReturn("Password");
        when(userDetails.getPassword()).thenReturn("Incorrect");

        String error = "";
        try {
            userService.loginUser(userDetails);
        } catch (IllegalStateException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Invalid credentials");
    }
    
    @Test
    void getUserInfoTest() {
        when(tokenService.getAssociatedUser("Token")).thenReturn(user);
        
        User result = null;
        try {
            result = userService.getUserInfo("Token");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        assertEquals(user, result);
    }

    @Test
    void getUserByUsernameTest_UserExists() {
        when(userRepository.existsUserByUsername("Username")).thenReturn(true);
        when(userRepository.findUserByUsername("Username")).thenReturn(user);

        User result = userService.getUserByUsername("Username");

        assertEquals(result, user);
    }

    @Test
    void getUserByUsernameTest_UserDoesNotExist() {
        when(userRepository.existsUserByUsername("Username")).thenReturn(false);

        String error = "";
        try {
            userService.getUserByUsername("Username");
        } catch (EntityNotFoundException e) {
            error = e.getMessage();
        }

        assertEquals(error, "User not found");
    }

    @Test
    void resetPasswordTest_Success() {
        when(userRepository.existsUserByEmail(userDetails.getEmail())).thenReturn(true);
        when(userRepository.findUserByEmail(userDetails.getEmail())).thenReturn(user);
        when(userDetails.getSecurityQuestionAnswer()).thenReturn("Answer");
        when(user.getSecurityQuestionAnswer()).thenReturn("Answer");

        String result = "";
        try {
            result = userService.resetPassword(userDetails);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(result, "Password reset successful");
    }

    @Test
    void resetPasswordTest_UserDoesNotExist() {
        when(userRepository.existsUserByEmail(userDetails.getEmail())).thenReturn(false);

        String error = "";
        try {
            userService.resetPassword(userDetails);
        } catch (InvalidSecurityAnswerException e) {
            fail();
        } catch (CredentialNotFoundException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Password reset failed");
    }

    @Test
    void resetPasswordTest_WrongAnswer() {
        when(userRepository.existsUserByEmail(userDetails.getEmail())).thenReturn(true);
        when(userRepository.findUserByEmail(userDetails.getEmail())).thenReturn(user);
        when(userDetails.getSecurityQuestionAnswer()).thenReturn("Wrong Answer");
        when(user.getSecurityQuestionAnswer()).thenReturn("Answer");

        String error = "";
        try {
            userService.resetPassword(userDetails);
        } catch (InvalidSecurityAnswerException e) {
            error = e.getMessage();
        } catch (CredentialNotFoundException e) {
            fail();
        }

        assertEquals(error, "Password reset failed");
    }

    @Test
    void getSecurityQuestionTest_Valid() {
        when(userRepository.existsUserByEmail("email")).thenReturn(true);
        when(userRepository.findUserByEmail("email")).thenReturn(user);
        when(user.getSecurityQuestion()).thenReturn("Question");

        String result  = "";
        try {
            result = userService.getSecurityQuestion("email");
        } catch (CredentialNotFoundException e) {
            fail();
        }

        assertEquals(result, "Question");
    }

    @Test
    void getSecurityQuestionTest_NullEmail() {
        String error = "";
        try {
            userService.getSecurityQuestion(null);
        } catch (IllegalArgumentException | CredentialNotFoundException e) {
            error = e.getMessage();
        }

        assertEquals(error, "A email must be provided");
    }

    @Test
    void getSecurityQuestionTest_UserDoesNotExist() {
        when(userRepository.existsUserByEmail("email")).thenReturn(false);

        String error = "";
        try {
            userService.getSecurityQuestion("email");
        } catch (IllegalArgumentException | CredentialNotFoundException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Email not found");
    }
}
