package gourp77.token.service;

import gourp77.token.Token;
import gourp77.token.TokenRepository;
import gourp77.token.service.TokenServiceImpl;
import gourp77.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TokenServiceImplTests {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private User user;

    @Mock
    private Token token;

    @Test
    void validateTokenTest_Valid() {
        when(tokenRepository.findTokenByToken(anyString())).thenReturn(token);
        when(token.getExpiresAt()).thenReturn(LocalDateTime.MAX);

        String result = "";
        try {
            result = tokenService.validateToken("Token");
        } catch (CredentialNotFoundException | CredentialExpiredException e) {
            fail(e.getMessage());
        }

        assertEquals(result,"Valid token");
    }

    @Test
    void validateTokenTest_MissingArgument() {
        String error = "";
        try {
            tokenService.validateToken(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }catch (CredentialNotFoundException | CredentialExpiredException e) {
            fail(e.getMessage());
        }

        assertEquals(error, "A token parameter must be provided");
    }

    @Test
    void validateTokenTest_Invalid() {
        when(tokenRepository.findTokenByToken(anyString())).thenReturn(null);
        
        String error = "";
        try {
            tokenService.validateToken("Token");
        } catch (CredentialNotFoundException e) {
            error = e.getMessage();
        } catch (CredentialExpiredException e) {
            fail(e.getMessage());
        }
        
        assertEquals(error, "Invalid token");
    }

    @Test
    void validateTokenTest_Expired() {
        when(tokenRepository.findTokenByToken(anyString())).thenReturn(token);
        when(token.getExpiresAt()).thenReturn(LocalDateTime.MIN);

        String error = "";
        try {
            tokenService.validateToken("Token");
        } catch (CredentialExpiredException e) {
            error = e.getMessage();
        } catch (CredentialNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(error, "Expired token");
    }

    @Test
    void getAssociatedUserTest() {
        when(tokenRepository.findTokenByToken(anyString())).thenReturn(token);
        when(token.getUser()).thenReturn(user);

        User result = tokenService.getAssociatedUser("Token");

        assertEquals(result, user);
    }

    @Test
    void generateTokenTest() {
        Token token = new Token(user);
        assertEquals(token.getUser(), tokenService.generateToken(user).getUser());
    }
}
