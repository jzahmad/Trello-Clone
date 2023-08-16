package gourp77.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gourp77.task.Task;
import gourp77.workspace.Workspace;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(orphanRemoval = true, mappedBy = "board")
    private final List<Task> tasks = new LinkedList<>();

    public Board(String name) {
        this.name = name;
    }

    public Board() {
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

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task t) {
        tasks.add(t);
    }
}
