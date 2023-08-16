package gourp77.token.service;

import gourp77.token.Token;
import gourp77.user.User;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

public interface ITokenService {
    Token generateToken(User user);

    String validateToken(String tokenString) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException;

    User getAssociatedUser(String token);
}
