package gourp77.board.service;

import gourp77.board.Board;
import gourp77.board.BoardRepository;
import gourp77.workspace.Workspace;
import gourp77.workspace.service.IWorkspaceService;
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

public class BoardServiceImplTests {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private IWorkspaceService workspaceService;

    @InjectMocks
    @Spy
    private BoardServiceImpl boardService;

    @Mock
    private Workspace workspace;

    @Mock
    private Board board;

    @Test
    void addBoardTest_Valid() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString())).thenReturn(workspace);

        Board result = boardService.addBoard(1, "Token", "Board");

        assertNotNull(result);
    }

    @Test
    void addBoardTest_NullBoardName() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(workspaceService.getWorkspace(anyLong(), anyString())).thenReturn(workspace);

        String error = "";
        try {
            Board result = boardService.addBoard(1, "Token", null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals(error, "A board name must be provided");
    }

    @Test
    void getBoardTest_Valid() {
        when(boardRepository.existsById(anyLong())).thenReturn(true);
        when(boardRepository.getBoardById(anyLong())).thenReturn(board);
        when(board.getWorkspace()).thenReturn(workspace);
        when(workspace.getId()).thenReturn(1L);

        Board result = null;
        try {
            result = boardService.getBoard(1, "Token");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(result, board);
    }

    @Test
    void getBoardTest_BoardDoesNotExist() {
        when(boardRepository.existsById(anyLong())).thenReturn(false);

        String error = "";
        try {
            boardService.getBoard(1, "Token");
        } catch (EntityNotFoundException e) {
            error = e.getMessage();
        } catch (CredentialExpiredException | CredentialNotFoundException | AccessDeniedException e) {
            fail(e.getMessage());
        }

        assertEquals(error, "Board not found");
    }

    @Test
    void deleteBoardTest() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        doReturn(board).when(boardService).getBoard(anyLong(), anyString());

        String result = "";
        try {
            result = boardService.deleteBoard(1, "Token");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(result, "Board deleted successfully");
    }
}
