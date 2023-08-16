package gourp77.token.service;

import gourp77.token.Token;
import gourp77.token.TokenRepository;
import gourp77.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements ITokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token generateToken(User user) {
        Token token = new Token(user);
        tokenRepository.save(token);
        return token;
    }

    @Override
    public String validateToken(String tokenString) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException {
        // Check for missing arguments
        if (tokenString == null) {
            throw new IllegalArgumentException("A token parameter must be provided");
        }

        Token token = tokenRepository.findTokenByToken(tokenString);

        // Check if the token wasn't found
        if (token == null) {
            throw new CredentialNotFoundException("Invalid token");
        }

        // Check if the token is expired
        boolean tokenExpired = token.getExpiresAt().isBefore(LocalDateTime.now());
        if (tokenExpired) {
            throw new CredentialExpiredException("Expired token");
        }

        // Extends login time when an call is made using the token
        token.extendExpiryTime();
        tokenRepository.save(token);

        return "Valid token";
    }

    @Override
    public User getAssociatedUser(String token) {
        return tokenRepository.findTokenByToken(token).getUser();
    }
}
