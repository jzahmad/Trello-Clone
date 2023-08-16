package gourp77.task.service;

import gourp77.board.Board;
import gourp77.board.service.IBoardService;
import gourp77.task.Task;
import gourp77.task.TaskRepository;
import gourp77.task.enums.TaskStatus;
import gourp77.task.service.TaskServiceImpl;
import gourp77.user.User;
import gourp77.user.service.IUserService;
import gourp77.workspace.Workspace;
import gourp77.workspace.service.IWorkspaceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private IBoardService boardService;

    @Mock
    private IUserService userService;

    @Mock
    private IWorkspaceService workspaceService;

    @InjectMocks
    @Spy
    private TaskServiceImpl taskService;

    @Mock
    private Workspace workspace;

    @Mock
    private Board board;

    @Mock
    private Task task;

    @Mock
    private User user;

    @Test
    void updateTaskTest() {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(true);
        when(taskRepository.getTaskById(anyLong())).thenReturn(task);

        LocalDate date = LocalDate.now();
        assertTrue(taskService.update(1L, date), "due date of task should be updated");
    }

    @Test
    void updateTaskTest_deleteDueDate() {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(true);
        when(taskRepository.getTaskById(anyLong())).thenReturn(task);

        assertTrue(taskService.update(1L, null), "due date of task should be deleted");
    }

    @Test
    void updateTaskTest_TaskDoesNotExist() {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(false);

        LocalDate date = LocalDate.now();
        try {
            taskService.update(1L, date);
        } catch (EntityNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void changeStatusTest() {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(true);
        when(taskRepository.getTaskById(anyLong())).thenReturn(task);

        TaskStatus status = TaskStatus.TODO;
        assertTrue(taskService.changeStatus(1L, status), "status of task should be changed");
    }

    @Test
    void changeStatusTest_TaskDoesNotExist() {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(false);

        TaskStatus status = TaskStatus.DOING;
        try {
            taskService.changeStatus(1L, status);
        } catch (EntityNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void addTaskTest() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(boardService.getBoard(anyLong(), anyString())).thenReturn(board);

        Task taskInput = new Task("Name", TaskStatus.TODO);
        Task taskOutput = null;
        try {
            taskOutput = taskService.addTask(1L, "Token", taskInput);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        boolean pass = taskInput.getName().equals(taskOutput.getName())
                && taskInput.getStatus() == taskOutput.getStatus()
                && board == taskOutput.getBoard();
        assertTrue(pass);
    }

    @Test
    void addTaskTest_MissingName() {
        Task taskInput = new Task(null, TaskStatus.TODO);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> taskService.addTask(1L, "Token", taskInput));

        assertEquals(result.getMessage(), "A task name must be provided");
    }

    @Test
    void addTaskTest_MissingStatus() {
        Task taskInput = new Task("Name", null);

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> taskService.addTask(1L, "Token", taskInput));

        assertEquals(result.getMessage(), "A task status must be provided");
    }

    @Test
    void assignMemberTest() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(true);
        when(taskRepository.getTaskById(anyLong())).thenReturn(task);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        doReturn(true).when(taskService).memberCanBeAssigned(any(Task.class), anyString(), any(User.class));

        String result = "";
        try {
            result = taskService.assignMember(1L, "Token", "User");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        verify(task).setAssignedUser(user);
        verify(taskRepository).save(task);
        assertEquals("Member successfully assigned!", result);
    }

    @Test
    void assignMemberTest_BlankAssignedMember() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(true);
        when(taskRepository.getTaskById(anyLong())).thenReturn(task);

        String result = "";
        try {
            result = taskService.assignMember(1L, "Token", "");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        verify(task).setAssignedUser(null);
        verify(taskRepository).save(task);
        assertEquals("Member successfully removed!", result);
    }

    @Test
    void assignMemberTest_TaskDoesNotExist() {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(false);

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class,
                () -> taskService.assignMember(1L, "Token", "User")
        );

        assertEquals("Task not found", result.getMessage());
    }

    @Test
    void assignMemberTest_MemberCannotBeAssigned() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskRepository.existsTaskById(anyLong())).thenReturn(true);
        when(taskRepository.getTaskById(anyLong())).thenReturn(task);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        doReturn(false).when(taskService).memberCanBeAssigned(any(Task.class), anyString(), any(User.class));

        IllegalStateException result = assertThrows(
                IllegalStateException.class,
                () -> taskService.assignMember(1L, "Token", "User")
        );

        assertEquals("This member cannot be assigned", result.getMessage());
    }

    @Test
    void memberCanBeAssignedTest_True() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(task.getBoard()).thenReturn(board);
        when(board.getWorkspace()).thenReturn(workspace);
        when(workspace.containsMember(user)).thenReturn(true);

        boolean result = false;
        try {
            result = taskService.memberCanBeAssigned(task, "Token", user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        verify(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        assertTrue(result);
    }

    @Test
    void memberCanBeAssignedTest_False() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(task.getBoard()).thenReturn(board);
        when(board.getWorkspace()).thenReturn(workspace);
        when(workspace.containsMember(user)).thenReturn(false);

        boolean result = true;
        try {
            result = taskService.memberCanBeAssigned(task, "Token", user);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        verify(workspaceService).checkWorkspaceAccessPrivileges(anyLong(), anyString());
        assertFalse(result);
    }
}