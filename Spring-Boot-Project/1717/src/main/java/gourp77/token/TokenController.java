package gourp77.token;

import gourp77.token.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final ITokenService tokenService;

    @Autowired
    public TokenController(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        try {
            return new ResponseEntity<>(tokenService.validateToken(token), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
