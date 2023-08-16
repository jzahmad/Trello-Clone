package gourp77.user.userDetails;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class UserDetailsTests {

    @Test
    void assertHasRequiredRegistrationInfoTest_CorrectInfo() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                "password",
                "question",
                "answer"
        );

        assertDoesNotThrow(userDetails::assertHasRequiredRegistrationInfo);
    }

    @Test
    void assertHasRequiredRegistrationInfoTest_NoUsername() {
        UserDetails userDetails = new UserDetails(
                null,
                "email",
                "password",
                "question",
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredRegistrationInfo);
        assertEquals("A username must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredRegistrationInfoTest_NoEmail() {
        UserDetails userDetails = new UserDetails(
                "username",
                null,
                "password",
                "question",
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredRegistrationInfo);
        assertEquals("An email must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredRegistrationInfoTest_NoPassword() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                null,
                "question",
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredRegistrationInfo);
        assertEquals("A password must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredRegistrationInfoTest_NoSecurityQuestion() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                "password",
                null,
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredRegistrationInfo);
        assertEquals("A security question must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredRegistrationInfoTest_NoSecurityAnswer() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                "password",
                "question",
                null
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredRegistrationInfo);
        assertEquals("A security question answer must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredLoginInfoTest_CorrectInfo() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                "password",
                "question",
                "answer"
        );

        assertDoesNotThrow(userDetails::assertHasRequiredLoginInfo);
    }

    @Test
    void assertHasRequiredLoginInfoTest_NoEmail() {
        UserDetails userDetails = new UserDetails(
                "username",
                null,
                "password",
                "question",
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredLoginInfo);
        assertEquals("An email must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredLoginInfoTest_NoPassword() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                null,
                "question",
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredLoginInfo);
        assertEquals("A password must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredPasswordResetInfoTest_CorrectInfo() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                "password",
                "question",
                "answer"
        );

        assertDoesNotThrow(userDetails::assertHasRequiredPasswordResetInfo);
    }

    @Test
    void assertHasRequiredPasswordResetInfoTest_NoEmail() {
        UserDetails userDetails = new UserDetails(
                "username",
                null,
                "password",
                "question",
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredPasswordResetInfo);
        assertEquals("An email must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredPasswordResetInfoTest_NoSecurityAnswer() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                "password",
                "question",
                null
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredPasswordResetInfo);
        assertEquals("A security question answer must be provided", e.getMessage());
    }

    @Test
    void assertHasRequiredPasswordResetInfoTest_NoPassword() {
        UserDetails userDetails = new UserDetails(
                "username",
                "email",
                null,
                "question",
                "answer"
        );

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                userDetails::assertHasRequiredPasswordResetInfo);
        assertEquals("A new password must be provided", e.getMessage());
    }
}
