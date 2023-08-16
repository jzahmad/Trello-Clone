package gourp77.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsTaskById(long id);
    Task getTaskById(long id);
}
