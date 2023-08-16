package gourp77.workspace.service;

import gourp77.token.service.ITokenService;
import gourp77.user.User;
import gourp77.user.service.IUserService;
import gourp77.workspace.Workspace;
import gourp77.workspace.WorkspaceRepository;
import gourp77.workspace.service.WorkspaceServiceImpl;
import gourp77.workspace.workspaceDetails.WorkspaceDetails;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class WorkspaceServiceImplTests {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private ITokenService tokenService;

    @Mock
    private IUserService userService;

    @InjectMocks
    @Spy
    private WorkspaceServiceImpl workspaceService;

    @Mock
    private User user;

    @Mock
    private Workspace workspace;

    @Test
    void createWorkspaceTest_Valid() {
        when(tokenService.getAssociatedUser(anyString())).thenReturn(user);

        Workspace result = null;
        try {
            result = workspaceService.createWorkspace("Token", new WorkspaceDetails(
                    "Name",
                    "Description"
            ));
        } catch (CredentialNotFoundException | CredentialExpiredException e) {
            fail(e.getMessage());
        }

        assertNotNull(result);
    }

    @Test
    void createWorkspaceTest_NoName() {
        when(tokenService.getAssociatedUser(anyString())).thenReturn(user);

        String error = "";
        try {
            workspaceService.createWorkspace("Token", new WorkspaceDetails(
                    null,
                    "Description"
            ));
        } catch (CredentialNotFoundException | CredentialExpiredException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "A workspace name must be provided");
    }

    @Test
    void checkWorkspaceAccessPrivilegesTest_WorkspaceDoesNotExist() {
        when(tokenService.getAssociatedUser(anyString())).thenReturn(user);
        when(workspaceRepository.existsWorkspaceById(anyLong())).thenReturn(false);

        String error = "";
        try {
            workspaceService.checkWorkspaceAccessPrivileges(1L, "Token");
        } catch (CredentialExpiredException | CredentialNotFoundException | AccessDeniedException e) {
            fail(e.getMessage());
        } catch (EntityNotFoundException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Workspace not found");
    }

    @Test
    void checkWorkspaceAccessPrivilegesTest_AccessDenied() {
        when(tokenService.getAssociatedUser(anyString())).thenReturn(user);
        when(workspaceRepository.existsWorkspaceById(anyLong())).thenReturn(true);
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);
        when(workspace.containsMember(user)).thenReturn(false);

        String error = "";
        try {
            workspaceService.checkWorkspaceAccessPrivileges(1L, "Token");
        } catch (CredentialExpiredException | CredentialNotFoundException e) {
            fail(e.getMessage());
        } catch (AccessDeniedException e) {
            error = e.getMessage();
        }

        assertEquals(error, "Access denied");
    }

    @Test
    void checkWorkspaceAccessPrivilegesTest_Valid() {
        when(tokenService.getAssociatedUser(anyString())).thenReturn(user);
        when(workspaceRepository.existsWorkspaceById(anyLong())).thenReturn(true);
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);
        when(workspace.containsMember(user)).thenReturn(true);

        try {
            workspaceService.checkWorkspaceAccessPrivileges(1L, "Token");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getWorkspaceTest() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doNothing().when(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);

        Workspace result = null;
        try {
            result = workspaceService.getWorkspace(1L, "Token");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(result, workspace);
    }

    @Test
    void deleteWorkspaceTest() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doReturn(workspace).when(workspaceService).getWorkspace(anyLong(), anyString());

        String result = workspaceService.deleteWorkspace(1L, "Token");

        assertEquals(result, "Workspace deleted successfully!");
    }

    @Test
    void updateDetailsTest_Valid() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doNothing().when(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);

        String result = "";
        try {
            result = workspaceService.updateDetails(1L, "Token", new WorkspaceDetails(
                    "Name",
                    "Description"
            ));
        } catch (CredentialExpiredException | CredentialNotFoundException | AccessDeniedException e) {
            fail(e.getMessage());
        }

        assertEquals(result, "Details updated successfully");
    }

    @Test
    void updateDetailsTest_NoName() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doNothing().when(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);

        String error = "";
        try {
            workspaceService.updateDetails(1L, "Token", new WorkspaceDetails(
                    null,
                    "Description"
            ));
        } catch (CredentialExpiredException | CredentialNotFoundException | AccessDeniedException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "A workspace name must be included");
    }

    @Test
    void addMemberTest_Valid() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doNothing().when(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(workspace.containsMember(any(User.class))).thenReturn(false);

        String result = workspaceService.addMember(1L, "Token", "User");

        assertEquals(result, "Member added successfully");
    }

    @Test
    void addMemberTest_NoUsername() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doNothing().when(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);

        String error = "";
        try {
            workspaceService.addMember(1L, "Token", null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "A user to add must be provided");
    }

    @Test
    void addMemberTest_UserAlreadyMember() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doNothing().when(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        when(workspaceRepository.getWorkspaceById(anyLong())).thenReturn(workspace);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(workspace.containsMember(any(User.class))).thenReturn(true);

        String error = "";
        try {
            workspaceService.addMember(1L, "Token", "User");
        } catch (IllegalStateException e) {
            error = e.getMessage();
        }

        assertEquals(error, "User is already a member");
    }
}
