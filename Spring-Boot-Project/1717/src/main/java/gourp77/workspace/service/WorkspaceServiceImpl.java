package gourp77.workspace.service;

import gourp77.token.service.ITokenService;
import gourp77.user.User;
import gourp77.user.service.IUserService;
import gourp77.workspace.Workspace;
import gourp77.workspace.WorkspaceRepository;
import gourp77.workspace.workspaceDetails.WorkspaceDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;

@Service
public class WorkspaceServiceImpl implements IWorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final ITokenService tokenService;

    private final IUserService userService;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository, ITokenService tokenService,
                                IUserService userService) {
        this.workspaceRepository = workspaceRepository;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public Workspace createWorkspace(String token, WorkspaceDetails workspaceDetails) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException {
        tokenService.validateToken(token);
        User creator = tokenService.getAssociatedUser(token);

        if (workspaceDetails.getName() == null)
            throw new IllegalArgumentException("A workspace name must be provided");

        Workspace workspace = new Workspace(workspaceDetails.getName(), workspaceDetails.getDescription());
        workspace.addMember(creator);
        workspaceRepository.save(workspace);

        return workspace;
    }

    @Override
    public void checkWorkspaceAccessPrivileges(long workspaceId, String token) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        tokenService.validateToken(token);
        User user = tokenService.getAssociatedUser(token);

        if (!workspaceRepository.existsWorkspaceById(workspaceId))
            throw new EntityNotFoundException("Workspace not found");

        Workspace workspace = workspaceRepository.getWorkspaceById(workspaceId);
        if (!workspace.containsMember(user))
            throw new AccessDeniedException("Access denied");
    }

    @Override
    public Workspace getWorkspace(long id, String token) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        checkWorkspaceAccessPrivileges(id, token);
        return workspaceRepository.getWorkspaceById(id);
    }

    @Override
    public String deleteWorkspace(long id, String token) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        Workspace workspace = getWorkspace(id, token);
        workspaceRepository.delete(workspace);

        return "Workspace deleted successfully!";
    }

    @Override
    public String updateDetails(long workspaceId, String token, WorkspaceDetails workspaceDetails) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        checkWorkspaceAccessPrivileges(workspaceId, token);
        Workspace workspace = workspaceRepository.getWorkspaceById(workspaceId);

        if (workspaceDetails.getName() == null)
            throw new IllegalArgumentException("A workspace name must be included");

        workspace.setName(workspaceDetails.getName());
        workspace.setDescription(workspaceDetails.getDescription());
        workspaceRepository.save(workspace);
        return "Details updated successfully";
    }

    @Override
    public String addMember(long workspaceId, String token, String username) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException, IllegalStateException {
        checkWorkspaceAccessPrivileges(workspaceId, token);
        Workspace workspace = workspaceRepository.getWorkspaceById(workspaceId);

        if (username == null)
            throw new IllegalArgumentException("A user to add must be provided");

        User userToAdd = userService.getUserByUsername(username);

        if (workspace.containsMember(userToAdd))
            throw new IllegalStateException("User is already a member");

        workspace.addMember(userToAdd);
        workspaceRepository.save(workspace);
        return "Member added successfully";
    }
}
