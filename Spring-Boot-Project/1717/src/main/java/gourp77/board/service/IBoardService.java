package gourp77.board.service;

import gourp77.board.Board;
import jakarta.persistence.EntityNotFoundException;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;

public interface IBoardService {
    Board addBoard(long workspaceId, String token, String boardName) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException;

    Board getBoard(long boardId, String token) throws
                    IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
                    EntityNotFoundException, AccessDeniedException;

    String deleteBoard(long boardId, String token) throws
                            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
                            EntityNotFoundException, AccessDeniedException;
}
