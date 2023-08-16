package gourp77.user.service;

import gourp77.exceptions.InvalidSecurityAnswerException;
import gourp77.token.Token;
import gourp77.token.service.ITokenService;
import gourp77.user.User;
import gourp77.user.UserRepository;
import gourp77.user.userDetails.UserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ITokenService tokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ITokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public String createUser(UserDetails userDetails) throws IllegalArgumentException, IllegalStateException {
        // Check for missing arguments
        userDetails.assertHasRequiredRegistrationInfo();

        // Check if the username is taken
        if (userRepository.existsUserByUsername(userDetails.getUsername())) {
            throw new IllegalStateException("Username already in use");
        }
        // Check if the email is taken
        if (userRepository.existsUserByEmail(userDetails.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }

        // Save user in database
        userRepository.save(new User(userDetails));
        return "User created successfully";
    }

    @Override
    public String loginUser(UserDetails userDetails) throws IllegalArgumentException, IllegalStateException {
        // Check for missing arguments
        userDetails.assertHasRequiredLoginInfo();

        // Check if credentials are valid
        boolean validCredentials;
        if (userRepository.existsUserByEmail(userDetails.getEmail())) {
            String passwordMatchingProvidedEmail =
                    userRepository.findUserByEmail(userDetails.getEmail()).getPassword();
            validCredentials = userDetails.getPassword().equals(passwordMatchingProvidedEmail);
        } else {
            validCredentials = false;
        }

        // Generate response message
        if (validCredentials) {
            // Return a newly generated token string
            User validatedUser = userRepository.findUserByEmail(userDetails.getEmail());
            Token token = tokenService.generateToken(validatedUser);
            return token.getToken();
        } else {
            throw new IllegalStateException("Invalid credentials");
        }
    }

    @Override
    public User getUserInfo(String token) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException {
        tokenService.validateToken(token);
        return tokenService.getAssociatedUser(token);
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException {
        if (!userRepository.existsUserByUsername(username))
            throw new EntityNotFoundException("User not found");

        return userRepository.findUserByUsername(username);
    }

    @Override
    public String resetPassword(UserDetails userDetails) throws
            IllegalArgumentException, CredentialNotFoundException, InvalidSecurityAnswerException {
        // Check for missing arguments
        userDetails.assertHasRequiredPasswordResetInfo();

        User user;
        if (userRepository.existsUserByEmail(userDetails.getEmail())) {
            user = userRepository.findUserByEmail(userDetails.getEmail());
        } else {
            throw new CredentialNotFoundException("Password reset failed");
        }

        if (userDetails.getSecurityQuestionAnswer().equals(user.getSecurityQuestionAnswer())) {
            user.setPassword(userDetails.getPassword());
            userRepository.save(user);
            return "Password reset successful";
        } else {
            throw new InvalidSecurityAnswerException("Password reset failed");
        }
    }

    @Override
    public String getSecurityQuestion(String email) throws CredentialNotFoundException {
        if (email == null) {
            throw new IllegalArgumentException("A email must be provided");
        }

        if (userRepository.existsUserByEmail(email)) {
            User user = userRepository.findUserByEmail(email);
            return user.getSecurityQuestion();
        } else {
            throw new CredentialNotFoundException("Email not found");
        }
    }
}
