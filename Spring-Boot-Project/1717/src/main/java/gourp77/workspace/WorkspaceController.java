package gourp77.workspace;

import gourp77.workspace.service.IWorkspaceService;
import gourp77.workspace.workspaceDetails.WorkspaceDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;

@CrossOrigin
@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final IWorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(IWorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWorkspace(@RequestParam String token, @RequestBody WorkspaceDetails workspaceDetails) {
        try {
            return new ResponseEntity<>(workspaceService.createWorkspace(token, workspaceDetails), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteWorkspace(@PathVariable Long id, @RequestParam String token) {
        try {
            return new ResponseEntity<>(workspaceService.deleteWorkspace(id, token), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException |
                EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkspace(@PathVariable long id, @RequestParam String token) {
        try {
            return new ResponseEntity<>(workspaceService.getWorkspace(id, token), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException |
                EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/update-details")
    public ResponseEntity<String> updateDetails(@PathVariable long id, @RequestParam String token, @RequestBody WorkspaceDetails details) {
        try {
            return new ResponseEntity<>(workspaceService.updateDetails(id, token, details), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException |
                EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/add-member")
    public ResponseEntity<String> addMember(@PathVariable long id, @RequestParam String token, @RequestParam String username) {
        try {
            return new ResponseEntity<>(workspaceService.addMember(id, token, username), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException |
                EntityNotFoundException | AccessDeniedException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
