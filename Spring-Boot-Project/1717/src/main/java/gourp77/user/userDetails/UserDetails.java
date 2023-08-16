package gourp77.user.userDetails;

public class UserDetails {
    private String username;
    private String email;
    private String password;
    private String securityQuestion;
    private String securityQuestionAnswer;

    public UserDetails(String username, String email, String password,
                String securityQuestion, String securityQuestionAnswer) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public UserDetails() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public void assertHasRequiredRegistrationInfo() throws IllegalArgumentException {
        if (username == null) {
            throw new IllegalArgumentException("A username must be provided");
        }
        if (email == null) {
            throw new IllegalArgumentException("An email must be provided");
        }
        if (password == null) {
            throw new IllegalArgumentException("A password must be provided");
        }
        if (securityQuestion == null) {
            throw new IllegalArgumentException("A security question must be provided");
        }
        if (securityQuestionAnswer == null) {
            throw new IllegalArgumentException("A security question answer must be provided");
        }
    }

    public void assertHasRequiredLoginInfo() throws IllegalArgumentException {
        if (email == null) {
            throw new IllegalArgumentException("An email must be provided");
        }
        if (password == null) {
            throw new IllegalArgumentException("A password must be provided");
        }
    }

    public void assertHasRequiredPasswordResetInfo() throws IllegalArgumentException {
        if (email == null) {
            throw new IllegalArgumentException("An email must be provided");
        }
        if (securityQuestionAnswer == null) {
            throw new IllegalArgumentException("A security question answer must be provided");
        }
        if (password == null) {
            throw new IllegalArgumentException("A new password must be provided");
        }
    }
}
