package gourp77.task;

import gourp77.task.enums.TaskStatus;
import gourp77.task.service.ITaskService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTests {

    @Mock
    private ITaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private Task task;

    @Test
    void addTask_Valid() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskService.addTask(anyLong(), anyString(), any(Task.class))).thenReturn(task);

        ResponseEntity<?> expect = new ResponseEntity<>(task, HttpStatus.OK);
        ResponseEntity<?> result = taskController.addTask(1L, "Token", new Task());

        assertEquals(expect, result);
    }

    @Test
    void addTask_CredentialNotFoundException() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskService.addTask(anyLong(), anyString(), any(Task.class)))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.addTask(1L, "Token", new Task());

        assertEquals(expect, result);
    }

    @Test
    void addTask_CredentialExpiredException() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskService.addTask(anyLong(), anyString(), any(Task.class)))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<?> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.addTask(1L, "Token", new Task());

        assertEquals(expect, result);
    }

    @Test
    void addTask_AccessDeniedException() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskService.addTask(anyLong(), anyString(), any(Task.class)))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<?> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.addTask(1L, "Token", new Task());

        assertEquals(expect, result);
    }

    @Test
    void addTask_IllegalArgumentException() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskService.addTask(anyLong(), anyString(), any(Task.class)))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.addTask(1L, "Token", new Task());

        assertEquals(expect, result);
    }

    @Test
    void addTask_EntityNotFoundException() throws CredentialNotFoundException, AccessDeniedException, CredentialExpiredException {
        when(taskService.addTask(anyLong(), anyString(), any(Task.class)))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<?> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.addTask(1L, "Token", new Task());

        assertEquals(expect, result);
    }

    @Test
    void update_valid() {
        when(taskService.update(anyLong(), any())).thenReturn(true);

        LocalDate date = LocalDate.now();

        ResponseEntity<?> expect = new ResponseEntity<>(true, HttpStatus.OK);
        ResponseEntity<?> result = taskController.update(1L, date);

        assertEquals(expect, result);
    }

    @Test
    void update_EntityNotFoundException() {
        when(taskService.update(anyLong(), any()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        LocalDate date = LocalDate.now();

        ResponseEntity<?> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.update(1L, date);

        assertEquals(expect, result);
    }

    @Test
    void update_IllegalArgumentException() {
        when(taskService.update(anyLong(), any()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        LocalDate date = LocalDate.now();

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.update(1L, date);

        assertEquals(expect, result);
    }

    @Test
    void update_IllegalStateException() {
        when(taskService.update(anyLong(), any()))
                .thenThrow(new IllegalStateException("IllegalStateException"));

        LocalDate date = LocalDate.now();

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalStateException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.update(1L, date);

        assertEquals(expect, result);
    }

    @Test
    void changeStatus_valid() {
        when(taskService.changeStatus(anyLong(), any())).thenReturn(true);

        TaskStatus status = TaskStatus.TODO;

        ResponseEntity<?> expect = new ResponseEntity<>(true, HttpStatus.OK);
        ResponseEntity<?> result = taskController.changeStatus(1L, status);

        assertEquals(expect, result);
    }

    @Test
    void changeStatus_EntityNotFoundException() {
        when(taskService.changeStatus(anyLong(), any()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        TaskStatus status = TaskStatus.DONE;

        ResponseEntity<?> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.changeStatus(1L, status);

        assertEquals(expect, result);
    }

    @Test
    void changeStatus_IllegalArgumentException() {
        when(taskService.changeStatus(anyLong(), any()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        TaskStatus status = TaskStatus.TODO;

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.changeStatus(1L, status);

        assertEquals(expect, result);
    }

    @Test
    void changeStatus_IllegalStateException() {
        when(taskService.changeStatus(anyLong(), any()))
                .thenThrow(new IllegalStateException("IllegalStateException"));

        TaskStatus status = TaskStatus.TODO;

        ResponseEntity<?> expect = new ResponseEntity<>("IllegalStateException", HttpStatus.BAD_REQUEST);
        ResponseEntity<?> result = taskController.changeStatus(1L, status);

        assertEquals(expect, result);
    }

    @Test
    void assignMember_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString())).thenReturn("Member successfully assigned!");

        ResponseEntity<String> expect = new ResponseEntity<>("Member successfully assigned!", HttpStatus.OK);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "Member");

        assertEquals(expect, result);
    }

    @Test
    void RemoveMember_Valid() throws AccessDeniedException, CredentialNotFoundException, CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString())).thenReturn("Member successfully removed!");

        ResponseEntity<String> expect = new ResponseEntity<>("Member successfully removed!", HttpStatus.OK);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "");

        assertEquals(expect, result);
    }

    @Test
    void assignMember_IllegalArgumentException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("IllegalArgumentException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalArgumentException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "Member");

        assertEquals(expect, result);
    }

    @Test
    void assignMember_CredentialExpiredException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString()))
                .thenThrow(new CredentialExpiredException("CredentialExpiredException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialExpiredException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "Member");

        assertEquals(expect, result);
    }

    @Test
    void assignMember_IllegalStateException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString()))
                .thenThrow(new IllegalStateException("IllegalStateException"));

        ResponseEntity<String> expect = new ResponseEntity<>("IllegalStateException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "Member");

        assertEquals(expect, result);
    }

    @Test
    void assignMember_CredentialNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString()))
                .thenThrow(new CredentialNotFoundException("CredentialNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("CredentialNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "Member");

        assertEquals(expect, result);
    }

    @Test
    void assignMember_EntityNotFoundException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString()))
                .thenThrow(new EntityNotFoundException("EntityNotFoundException"));

        ResponseEntity<String> expect = new ResponseEntity<>("EntityNotFoundException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "Member");

        assertEquals(expect, result);
    }

    @Test
    void assignMember_AccessDeniedException() throws AccessDeniedException, CredentialNotFoundException,
            CredentialExpiredException {
        when(taskService.assignMember(anyLong(), anyString(), anyString()))
                .thenThrow(new AccessDeniedException("AccessDeniedException"));

        ResponseEntity<String> expect = new ResponseEntity<>("AccessDeniedException", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = taskController.assignMember(1L, "Token", "Member");

        assertEquals(expect, result);
    }
}
