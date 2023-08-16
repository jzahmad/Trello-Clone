package gourp77.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gourp77.board.Board;
import gourp77.task.enums.TaskStatus;
import gourp77.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate dueDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

    public Task(String name, TaskStatus status) {
        this.name = name;
        this.status = status;
    }

    public Task(String name, TaskStatus status, Board board) {
        this.name = name;
        this.status = status;
        this.board = board;
    }

    public Task(String name, TaskStatus status, LocalDate dueDate, Board board) {
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
        this.board = board;
    }

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getAssignedUser() {
        if (assignedUser == null)
            return null;

        return assignedUser.getUsername();
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
}
