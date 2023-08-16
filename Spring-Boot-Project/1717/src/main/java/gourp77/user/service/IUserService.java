package gourp77.user.service;

import gourp77.exceptions.InvalidSecurityAnswerException;
import gourp77.user.User;
import gourp77.user.userDetails.UserDetails;
import jakarta.persistence.EntityNotFoundException;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

public interface IUserService {
    String createUser(UserDetails userDetails) throws IllegalArgumentException, IllegalStateException;

    String loginUser(UserDetails userDetails) throws IllegalArgumentException, IllegalStateException;

    User getUserInfo(String token) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException;

    User getUserByUsername(String username) throws EntityNotFoundException;

    String resetPassword(UserDetails userDetails) throws
                    IllegalArgumentException, CredentialNotFoundException, InvalidSecurityAnswerException;

    String getSecurityQuestion(String email) throws CredentialNotFoundException;
}
