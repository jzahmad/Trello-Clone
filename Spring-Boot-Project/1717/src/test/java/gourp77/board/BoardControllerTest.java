package gourp77.board;

import gourp77.board.service.IBoardService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class BoardControllerTest {

    @Mock
    private IBoardService boardService;

    @InjectMocks
    private BoardController boardController;

    @Mock
    private Board board;

    @Test
    void addBoard_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(boardService.addBoard(anyLong(), anyString(), anyString())).thenReturn(board);

        ResponseEntity<?> expect = new ResponseEntity<>(board, HttpStatus.OK);
        ResponseEntity<?> result = boardController.addBoard(1L, "Token", "Name");

        assertEquals(expect, result);
    }

    @Test
    void addBoard_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.addBoard(anyLong(), anyString(), anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.addBoard(1L, "Token", "Name");

        assertEquals(expect, result);
    }

    @Test
    void addBoard_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.addBoard(anyLong(), anyString(), anyString()))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<?> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.addBoard(1L, "Token", "Name");

        assertEquals(expect, result);
    }

    @Test
    void addBoard_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.addBoard(anyLong(), anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.addBoard(1L, "Token", "Name");

        assertEquals(expect, result);
    }

    @Test
    void addBoard_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.addBoard(anyLong(), anyString(), anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.addBoard(1L, "Token", "Name");

        assertEquals(expect, result);
    }

    @Test
    void addBoard_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.addBoard(anyLong(), anyString(), anyString()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.addBoard(1L, "Token", "Name");

        assertEquals(expect, result);
    }

    @Test
    void deleteBoard_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(boardService.deleteBoard(anyLong(), anyString())).thenReturn("Board deleted successfully");

        ResponseEntity<String> expect = new ResponseEntity<>("Board deleted successfully", HttpStatus.OK);
        ResponseEntity<String> result = boardController.deleteBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteBoard_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.deleteBoard(anyLong(), anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = boardController.deleteBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteBoard_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.deleteBoard(anyLong(), anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = boardController.deleteBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteBoard_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.deleteBoard(anyLong(), anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = boardController.deleteBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteBoard_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.deleteBoard(anyLong(), anyString()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = boardController.deleteBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void deleteBoard_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.deleteBoard(anyLong(), anyString()))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<String> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = boardController.deleteBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getBoard_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(boardService.getBoard(anyLong(), anyString())).thenReturn(board);

        ResponseEntity<?> expect = new ResponseEntity<>(board, HttpStatus.OK);
        ResponseEntity<?> result = boardController.getBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getBoard_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.getBoard(anyLong(), anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.getBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getBoard_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.getBoard(anyLong(), anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.getBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getBoard_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.getBoard(anyLong(), anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.getBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getBoard_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.getBoard(anyLong(), anyString()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.getBoard(1L, "Token");

        assertEquals(expect, result);
    }

    @Test
    void getBoard_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(boardService.getBoard(anyLong(), anyString()))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<?> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = boardController.getBoard(1L, "Token");

        assertEquals(expect, result);
    }
}