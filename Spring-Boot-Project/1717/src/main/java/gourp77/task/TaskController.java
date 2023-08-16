package gourp77.task;

import gourp77.task.enums.TaskStatus;
import gourp77.task.service.ITaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.CredentialNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/board/{boardId}/add-task")
    public ResponseEntity<?> addTask(@PathVariable long boardId,
                                     @RequestParam String token,
                                     @RequestBody Task taskDetails) {
        try {
            return new ResponseEntity<>(
                    taskService.addTask(boardId, token, taskDetails),
                    HttpStatus.OK
            );
        } catch (CredentialNotFoundException | CredentialExpiredException | AccessDeniedException |
                IllegalArgumentException | EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/{taskId}/assign-member")
    public ResponseEntity<String> assignMember(@PathVariable long taskId,
                                               @RequestParam String token,
                                               @RequestParam String member) {
        try {
            return new ResponseEntity<>(
                    taskService.assignMember(taskId, token, member),
                    HttpStatus.OK
            );
        } catch (IllegalArgumentException | CredentialExpiredException | IllegalStateException |
                CredentialNotFoundException | EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/{taskId}/update")
    public ResponseEntity<?> update(@PathVariable long taskId,
                                    @RequestParam LocalDate date) {
        try {
            return new ResponseEntity<>(
                    taskService.update(taskId, date),
                    HttpStatus.OK
            );
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/{taskId}/change-status")
    public ResponseEntity<?> changeStatus(@PathVariable long taskId,
                                          @RequestParam TaskStatus status) {
        try {
            return new ResponseEntity<>(
                    taskService.changeStatus(taskId, status),
                    HttpStatus.OK
            );
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
