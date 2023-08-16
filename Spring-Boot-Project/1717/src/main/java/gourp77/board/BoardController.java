package gourp77.board;

import gourp77.board.service.IBoardService;
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
@RequestMapping("/boards")
public class BoardController {

    private final IBoardService boardService;

    @Autowired
    public BoardController(IBoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/workspace/{workspaceId}/add-board")
    public ResponseEntity<?> addBoard(@PathVariable long workspaceId, @RequestParam String token, @RequestParam String name) {
        try {
            return new ResponseEntity<>(boardService.addBoard(workspaceId, token, name), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException |
                EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteBoard(@PathVariable long id, @RequestParam String token) {
        try {
            return new ResponseEntity<>(boardService.deleteBoard(id, token), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException |
                EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoard(@PathVariable long id, @RequestParam String token) {
        try {
            return new ResponseEntity<>(boardService.getBoard(id, token), HttpStatus.OK);
        } catch (IllegalArgumentException | CredentialNotFoundException | CredentialExpiredException |
                EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
