package gourp77.board.service;

import gourp77.board.Board;
import gourp77.board.BoardRepository;
import gourp77.workspace.Workspace;
import gourp77.workspace.service.IWorkspaceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;

@Service
public class BoardServiceImpl implements IBoardService {

    private final BoardRepository boardRepository;

    private final IWorkspaceService workspaceService;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, IWorkspaceService workspaceService) {
        this.boardRepository = boardRepository;
        this.workspaceService = workspaceService;
    }

    @Override
    public Board addBoard(long workspaceId, String token, String boardName) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        Workspace workspace = workspaceService.getWorkspace(workspaceId, token);

        if (boardName == null)
            throw new IllegalArgumentException("A board name must be provided");

        Board board = new Board(boardName);
        board.setWorkspace(workspace);
        boardRepository.save(board);
        return board;
    }

    @Override
    public Board getBoard(long boardId, String token) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        if (!boardRepository.existsById(boardId))
            throw new EntityNotFoundException("Board not found");

        Board board = boardRepository.getBoardById(boardId);

        Workspace workspace = board.getWorkspace();
        long workspaceId = workspace.getId();
        workspaceService.checkWorkspaceAccessPrivileges(workspaceId, token);

        return board;
    }

    @Override
    public String deleteBoard(long boardId, String token) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        Board board = getBoard(boardId, token);
        boardRepository.delete(board);

        return "Board deleted successfully";
    }


}
