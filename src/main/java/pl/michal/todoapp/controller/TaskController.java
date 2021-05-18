package pl.michal.todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michal.todoapp.model.Task;
import pl.michal.todoapp.model.TaskRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.warn("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id){
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        logger.warn("Exposing task by id: " + id);
        return ResponseEntity.ok(repository.findById(id).get());
    }

    @PostMapping("/tasks/create")
    ResponseEntity<?> createTask(@RequestBody @Valid Task toCreate){
        repository.save(toCreate);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate){
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

}
