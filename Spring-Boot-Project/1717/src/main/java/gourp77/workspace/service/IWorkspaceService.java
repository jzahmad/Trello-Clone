package gourp77.workspace.service;

import gourp77.workspace.Workspace;
import gourp77.workspace.workspaceDetails.WorkspaceDetails;
import jakarta.persistence.EntityNotFoundException;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;

public interface IWorkspaceService {
    Workspace createWorkspace(String token, WorkspaceDetails workspaceDetails) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException;

    void checkWorkspaceAccessPrivileges(long workspaceId, String token) throws
                    IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException;

    Workspace getWorkspace(long id, String token) throws
                            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
                            EntityNotFoundException, AccessDeniedException;

    String deleteWorkspace(long id, String token) throws
                                    IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
                                    EntityNotFoundException, AccessDeniedException;

    String updateDetails(long workspaceId, String token, WorkspaceDetails workspaceDetails) throws
                                            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
                                            EntityNotFoundException, AccessDeniedException;

    String addMember(long workspaceId, String token, String username) throws
                                                    IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
                                                    EntityNotFoundException, AccessDeniedException, IllegalStateException;
}
