package gourp77.task.service;

import gourp77.board.Board;
import gourp77.board.service.IBoardService;
import gourp77.task.Task;
import gourp77.task.TaskRepository;
import gourp77.task.enums.TaskStatus;
import gourp77.user.User;
import gourp77.user.service.IUserService;
import gourp77.workspace.Workspace;
import gourp77.workspace.service.IWorkspaceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final IBoardService boardService;
    private final IUserService userService;
    private final IWorkspaceService workspaceService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, IBoardService boardService, IUserService userService, IWorkspaceService workspaceService) {
        this.taskRepository = taskRepository;
        this.boardService = boardService;
        this.userService = userService;
        this.workspaceService = workspaceService;
    }

    @Override
    public Task addTask(long boardId, String token, Task taskDetails) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        if (taskDetails.getName() == null)
            throw new IllegalArgumentException("A task name must be provided");

        if (taskDetails.getStatus() == null)
            throw new IllegalArgumentException("A task status must be provided");

        Board board = boardService.getBoard(boardId, token);

        Task task = new Task(
                taskDetails.getName(),
                taskDetails.getStatus(),
                board
        );
        taskRepository.save(task);
        return task;
    }

    public boolean memberCanBeAssigned(Task task, String token, User assignedMember) throws
            IllegalArgumentException, CredentialNotFoundException, CredentialExpiredException,
            EntityNotFoundException, AccessDeniedException {
        Workspace workspace = task.getBoard().getWorkspace();
        workspaceService.checkWorkspaceAccessPrivileges(workspace.getId(), token);

        return workspace.containsMember(assignedMember);
    }

    @Override
    public String assignMember(long taskId, String token, String assignedMember) throws
            IllegalArgumentException, IllegalStateException, CredentialNotFoundException,
            CredentialExpiredException, EntityNotFoundException, AccessDeniedException {
        if (!taskRepository.existsTaskById(taskId))
            throw new EntityNotFoundException("Task not found");

        Task task = taskRepository.getTaskById(taskId);

        if (assignedMember.equals("")) {
            task.setAssignedUser(null);
            taskRepository.save(task);
            return "Member successfully removed!";
        }

        User user = userService.getUserByUsername(assignedMember);

        if (!memberCanBeAssigned(task, token, user))
            throw new IllegalStateException("This member cannot be assigned");

        task.setAssignedUser(user);
        taskRepository.save(task);
        return "Member successfully assigned!";
    }

    @Override
    public boolean update(long taskId, LocalDate date) {
        if (taskRepository.existsTaskById(taskId)) {
            Task task = taskRepository.getTaskById(taskId);
            task.setDueDate(date);
            taskRepository.save(task);
            return true;
        } else {
            throw new EntityNotFoundException("Task not found");
        }
    }

    @Override
    public boolean changeStatus(long taskId, TaskStatus status) {
        if (taskRepository.existsTaskById(taskId)) {
            Task task = taskRepository.getTaskById(taskId);
            task.setStatus(status);
            taskRepository.save(task);
            return true;
        } else {
            throw new EntityNotFoundException("Task not found");
        }
    }
}
