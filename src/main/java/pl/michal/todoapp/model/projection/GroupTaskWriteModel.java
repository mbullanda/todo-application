package pl.michal.todoapp.model.projection;

import org.springframework.format.annotation.DateTimeFormat;
import pl.michal.todoapp.model.Task;
import pl.michal.todoapp.model.TaskGroup;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class GroupTaskWriteModel {
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Task toTask(TaskGroup group){
        return new Task(description, deadline, group);
    }
}
