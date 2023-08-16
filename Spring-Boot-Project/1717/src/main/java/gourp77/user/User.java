package gourp77.user;

import gourp77.task.Task;
import gourp77.user.userDetails.UserDetails;
import gourp77.workspace.Workspace;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;
    private String securityQuestion;
    private String securityQuestionAnswer;

    @ManyToMany(mappedBy = "members")
    private final List<Workspace> workspaces = new LinkedList<>();

    @OneToMany(mappedBy = "assignedUser")
    private final List<Task> tasks = new LinkedList<>();

    public User(String username, String email, String password,
                String securityQuestion, String securityQuestionAnswer) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public User(UserDetails userDetails) {
        this.username = userDetails.getUsername();
        this.email = userDetails.getEmail();
        this.password = userDetails.getPassword();
        this.securityQuestion = userDetails.getSecurityQuestion();
        this.securityQuestionAnswer = userDetails.getSecurityQuestionAnswer();
    }

    public User() {
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
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

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task t) {
        tasks.add(t);
    }
}
