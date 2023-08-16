package gourp77.task.service;

import gourp77.task.Task;
import gourp77.task.enums.TaskStatus;
import jakarta.persistence.EntityNotFoundException;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

public interface ITaskService {
    Task addTask(long boardId, String token, Task taskDetails) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException;

    String assignMember(long taskId, String token, String assignedMember) throws
                    IllegalArgumentException, IllegalStateException, CredentialNotFoundException,
                    CredentialExpiredException, EntityNotFoundException, AccessDeniedException;

    boolean update(long taskId, LocalDate date);

    boolean changeStatus(long taskId, TaskStatus status);
}
