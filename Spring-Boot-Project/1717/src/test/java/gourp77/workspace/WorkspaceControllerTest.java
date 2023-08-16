package gourp77.workspace;

import gourp77.workspace.service.IWorkspaceService;
import gourp77.workspace.workspaceDetails.WorkspaceDetails;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class WorkspaceControllerTest {

    @Mock
    private IWorkspaceService workspaceService;

    @InjectMocks
    private WorkspaceController workspaceController;

    @Mock
    private Workspace workspace;

    @Test
    void createWorkspace_Valid() throws CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.createWorkspace(anyString(), any(WorkspaceDetails.class))).thenReturn(workspace);

        ResponseEntity<?> expect = new ResponseEntity<>(workspace, HttpStatus.OK);
        ResponseEntity<?> result = workspaceController.createWorkspace("Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void createWorkspace_IllegalArgumentException() throws CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.createWorkspace(anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.createWorkspace("Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void createWorkspace_CredentialNotFoundException() throws CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.createWorkspace(anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new IllegalArgumentException("CredentialNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.createWorkspace("Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void createWorkspace_CredentialExpiredException() throws CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.createWorkspace(anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.createWorkspace("Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void deleteWorkspace_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.deleteWorkspace(anyLong(), anyString())).thenReturn("Workspace deleted successfully!");

        ResponseEntity<String> expect = new ResponseEntity<>("Workspace deleted successfully!", HttpStatus.OK);
        ResponseEntity<String> result = workspaceController.deleteWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteWorkspace_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.deleteWorkspace(anyLong(), anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.deleteWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteWorkspace_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.deleteWorkspace(anyLong(), anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.deleteWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteWorkspace_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.deleteWorkspace(anyLong(), anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.deleteWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteWorkspace_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.deleteWorkspace(anyLong(), anyString()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.deleteWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteWorkspace_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.deleteWorkspace(anyLong(), anyString()))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<String> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.deleteWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getWorkspace_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString())).thenReturn(workspace);

        ResponseEntity<?> expect = new ResponseEntity<>(workspace, HttpStatus.OK);
        ResponseEntity<?> result = workspaceController.getWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getWorkspace_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.getWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getWorkspace_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.getWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getWorkspace_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.getWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getWorkspace_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.getWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getWorkspace_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString()))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<?> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = workspaceController.getWorkspace(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void updateDetails_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.updateDetails(anyLong(), anyString(), any(WorkspaceDetails.class)))
                .thenReturn("Details updated successfully");

        ResponseEntity<String> expect = new ResponseEntity<>("Details updated successfully", HttpStatus.OK);
        ResponseEntity<String> result = workspaceController.updateDetails(1L, "Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void updateDetails_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.updateDetails(anyLong(), anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.updateDetails(1L, "Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void updateDetails_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.updateDetails(anyLong(), anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.updateDetails(1L, "Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void updateDetails_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.updateDetails(anyLong(), anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new IllegalArgumentException("CredentialExpiredException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.updateDetails(1L, "Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void updateDetails_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.updateDetails(anyLong(), anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.updateDetails(1L, "Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void updateDetails_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.updateDetails(anyLong(), anyString(), any(WorkspaceDetails.class)))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<String> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.updateDetails(1L, "Token", new WorkspaceDetails());

        assertEquals(expect, result);
    }

    @Test
    void addMember_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(workspaceService.addMember(anyLong(), anyString(), anyString())).thenReturn("Member added successfully");

        ResponseEntity<String> expect = new ResponseEntity<>("Member added successfully", HttpStatus.OK);
        ResponseEntity<String> result = workspaceController.addMember(1L, "Token", "Username");

        assertEquals(expect, result);
    }

    @Test
    void addMember_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.addMember(anyLong(), anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.addMember(1L, "Token", "Username");

        assertEquals(expect, result);
    }

    @Test
    void addMember_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.addMember(anyLong(), anyString(), anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.addMember(1L, "Token", "Username");

        assertEquals(expect, result);
    }

    @Test
    void addMember_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.addMember(anyLong(), anyString(), anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.addMember(1L, "Token", "Username");

        assertEquals(expect, result);
    }

    @Test
    void addMember_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.addMember(anyLong(), anyString(), anyString()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.addMember(1L, "Token", "Username");

        assertEquals(expect, result);
    }

    @Test
    void addMember_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.addMember(anyLong(), anyString(), anyString()))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<String> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.addMember(1L, "Token", "Username");

        assertEquals(expect, result);
    }

    @Test
    void addMember_IllegalStateException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(workspaceService.addMember(anyLong(), anyString(), anyString()))
                .thenThrow(new IllegalStateException("IllegalStateException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalStateException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = workspaceController.addMember(1L, "Token", "Username");

        assertEquals(expect, result);
    }
}