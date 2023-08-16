package gourp77.token;

import gourp77.token.service.ITokenService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class TokenControllerTest {

    @Mock
    private ITokenService tokenService;

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private Token token;

    @Test
    void validateToken() throws CredentialNotFoundException, CredentialExpiredException {
        when(tokenService.validateToken(anyString())).thenReturn("Valid token");

        ResponseEntity<String> expect = new ResponseEntity<>("Valid token", HttpStatus.OK);
        ResponseEntity<String> result = tokenController.validateToken("Token");

        assertEquals(expect, result);
    }

    @Test
    void validateToken_IllegalArgumentException() throws CredentialNotFoundException, CredentialExpiredException {
        when(tokenService.validateToken(anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = tokenController.validateToken("Token");

        assertEquals(expect, result);
    }

    @Test
    void validateToken_CredentialNotFoundException() throws CredentialNotFoundException, CredentialExpiredException {
        when(tokenService.validateToken(anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = tokenController.validateToken("Token");

        assertEquals(expect, result);
    }

    @Test
    void validateToken_CredentialExpiredException() throws CredentialNotFoundException, CredentialExpiredException {
        when(tokenService.validateToken(anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = tokenController.validateToken("Token");

        assertEquals(expect, result);
    }
}