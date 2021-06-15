package pl.michal.todoapp.model.event;

import pl.michal.todoapp.model.Task;

import java.time.Clock;

public class TaskDone extends TaskEvent {
    public TaskDone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
