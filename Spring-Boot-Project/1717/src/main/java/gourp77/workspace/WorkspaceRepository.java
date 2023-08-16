package gourp77.workspace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsWorkspaceById(long id);
    Workspace getWorkspaceById(long id);
}
