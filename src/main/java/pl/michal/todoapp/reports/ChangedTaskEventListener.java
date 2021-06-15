package pl.michal.todoapp.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.michal.todoapp.model.event.TaskDone;
import pl.michal.todoapp.model.event.TaskEvent;
import pl.michal.todoapp.model.event.TaskUndone;

@Service
class ChangedTaskEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);
    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(final PersistedTaskEventRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void on(TaskDone event){
        onChanged(event);
    }

    private void onChanged(final TaskEvent event) {
        logger.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }

    @EventListener
    public void on(TaskUndone event){
        onChanged(event);
    }

}
