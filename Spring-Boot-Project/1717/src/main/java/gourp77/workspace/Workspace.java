package gourp77.workspace;

import gourp77.board.Board;
import gourp77.user.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "user_workspace",
            joinColumns = @JoinColumn(name = "workspace_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> members = new HashSet<>();

    @OneToMany(orphanRemoval = true, mappedBy = "workspace")
    private final List<Board> boards = new LinkedList<>();

    public Workspace(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Workspace() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getMembers() {
        // Prevents circular reference and returned passwords
        Set<String> memberNames = new HashSet<>();
        for (User u : members) {
            memberNames.add(u.getUsername());
        }

        return memberNames;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addMember(User member) {
        members.add(member);
    }

    public boolean containsMember(User member) {
        return members.contains(member);
    }

    public void addBoard(Board board) {
        boards.add(board);
    }
}
