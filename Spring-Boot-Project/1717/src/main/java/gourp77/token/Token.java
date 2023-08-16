package gourp77.token;

import gourp77.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Token {

    public static final int SESSION_MINUTES = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private final String token;

    @Column(nullable = false)
    private final LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public Token(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(SESSION_MINUTES);
    }

    public Token() {
        this.token = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(SESSION_MINUTES);
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void extendExpiryTime() {
        this.expiresAt = LocalDateTime.now().plusMinutes(SESSION_MINUTES);
    }
}
