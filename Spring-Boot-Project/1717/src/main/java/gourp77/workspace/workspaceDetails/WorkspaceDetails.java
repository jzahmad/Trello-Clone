package gourp77.workspace.workspaceDetails;

public class WorkspaceDetails {

    private String name;
    private String description;

    public WorkspaceDetails(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public WorkspaceDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
